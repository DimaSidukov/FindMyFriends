package android.example.findmyfriends.data.source.remote

import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.remote.api.VkFriendsService
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.location.Geocoder
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(private val retrofit: Retrofit, private val geocoder: Geocoder) :
    RemoteSource {

    private lateinit var requestRetrofit: String
    private lateinit var service : VkFriendsService

    private val requestFriendsVK: String = "friends.get?access_token="
    private val miscURL: String = "&order=name&fields=city,domain,photo_100%20&&v=5.131"

    private fun setRequest(token: String) {
        requestRetrofit = requestFriendsVK + token + miscURL
    }

    private fun createService() {
        service = retrofit.create(VkFriendsService::class.java)
    }

    override fun getResponse(token: String): List<UserInfo> {

        setRequest(token)
        createService()

        val userInfoList = mutableListOf<UserInfo>()

        runBlocking {
            val result = service.getFriendList(requestRetrofit).response!!
            for (i in 0 until result.count!!) {
                if (result.items?.get(i)?.city?.title != null) {
                    val nameOfUser = result.items[i].firstName + " " + result.items[i].lastName
                    val user = UserInfo(
                        id = result.items[i].id!!,
                        name = nameOfUser,
                        city = result.items[i].city?.title!!,
                        photo_100 = result.items[i].photo100!!
                    )
                    userInfoList.add(user)
                }
            }
        }
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