package android.example.findmyfriends.repository.networkapi

import android.example.findmyfriends.model.local.isDbCreated
import android.example.findmyfriends.model.local.miscURL
import android.example.findmyfriends.model.local.requestFriendsVK
import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitInterfaceHandler @Inject constructor(val retrofit: Retrofit, val usersDao: DataBaseInterfaceHandler) : RetrofitInterface {

    private lateinit var requestRetrofit: String
    private lateinit var service : VkFriendsService

    override fun setRequest(token: String) {
        requestRetrofit = requestFriendsVK + token + miscURL
    }

    override fun createService() {
        service = retrofit.create(VkFriendsService::class.java)
    }

    override fun runRetrofit(token: String) : Boolean {

        setRequest(token)
        createService()

        service.getFriendList(requestRetrofit).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {

                val result = response.body()?.response!!

                for (i in 0 until result.count!!) {
                    if (result.items?.get(i)?.city?.title != null) {
                        val nameOfUser = result.items[i].first_name + " " + result.items[i].last_name
                        val user = UserInfo(
                            id = result.items[i].id!!,
                            name = nameOfUser,
                            city = result.items[i].city?.title!!,
                            photo_100 = result.items[i].photo_100!!
                        )
                        GlobalScope.launch(Dispatchers.IO) {
                            usersDao.insertToDataBase(user)
                        }
                    }
                }

                isDbCreated = true
            }

            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                isDbCreated = false
            }
        })

        return isDbCreated
    }

}
