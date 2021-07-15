package android.example.findmyfriends.repository

import android.example.findmyfriends.model.local.database.entity.UserInfo
import android.example.findmyfriends.model.local.plain.isDbCreated
import android.example.findmyfriends.model.local.source.LocalSource
import android.example.findmyfriends.model.remote.source.RemoteSource
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val localSource: LocalSource, private val remoteSource: RemoteSource) : RepositoryInterface {

    suspend fun retrieveData() = localSource.retrieveData()

    fun clearData() = localSource.clearData()

    fun loadMapData(users: List<UserInfo>) = remoteSource.loadMapData(users)

    override fun downloadData(token: String) : Boolean {

        remoteSource.setRequest(token)
        remoteSource.createService()
        remoteSource.service.getFriendList(remoteSource.requestRetrofit).enqueue(object : Callback<GetVkFriendsData> {
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
                        runBlocking {
                            localSource.loadData(user)
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