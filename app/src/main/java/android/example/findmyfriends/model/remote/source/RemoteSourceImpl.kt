package android.example.findmyfriends.model.remote.source

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.local.plain.miscURL
import android.example.findmyfriends.model.local.plain.requestFriendsVK
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.location.Geocoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(private val retrofit: Retrofit, private val geocoder: Geocoder) : RemoteSource {

    lateinit var requestRetrofit: String
    lateinit var service : VkFriendsService

    private fun setRequest(token: String) {
        requestRetrofit = requestFriendsVK + token + miscURL
    }

    private fun createService() {
        service = retrofit.create(VkFriendsService::class.java)
    }

    override fun getResponse(token: String, url: String): List<UserInfo> {

        setRequest(token)
        createService()

        val userInfoList = mutableListOf<UserInfo>()
        val call = service.getFriendList(url)
        call.enqueue(object : Callback<GetVkFriendsData> {
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
                        userInfoList.add(user)
                    }
                }
            }

            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
            }
        })

        return userInfoList
    }

    override fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData> {
        val listOfLocations = mutableListOf<UserLocationData>()
        val repetitiveCities = mutableListOf<String>()

        for (user in users) {
            if (!repetitiveCities.contains(user.city)) {
                val result = geocoder.getFromLocationName(user.city, 1)
                try {
                    listOfLocations.add(
                        UserLocationData(
                            result[0].latitude,
                            result[0].longitude,
                            user.name,
                            user.city
                        )
                    )
                } catch (e: Exception) { }
                repetitiveCities.add(user.city)
            }
        }

        return listOfLocations
    }
}