package android.example.findmyfriends.model.remote.source

import android.example.findmyfriends.model.local.database.entity.UserInfo
import android.example.findmyfriends.model.local.plain.miscURL
import android.example.findmyfriends.model.local.plain.requestFriendsVK
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.location.Geocoder
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteSource @Inject constructor(private val retrofit: Retrofit, private val geocoder: Geocoder) : RemoteSourceInterface {

    lateinit var requestRetrofit: String
    lateinit var service : VkFriendsService

    override fun setRequest(token: String) {
        requestRetrofit = requestFriendsVK + token + miscURL
    }

    override fun createService() {
        service = retrofit.create(VkFriendsService::class.java)
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