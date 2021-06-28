package android.example.findmyfriends.geoservice

import android.content.Context
import android.example.findmyfriends.vkdata.friendsinfo.Item
import android.location.Geocoder
import java.util.*

class GetCoordinatesByLocation(context: Context, private val users: List<Item>) {

    private val geocoder : Geocoder = Geocoder(context, Locale.getDefault())
    private var listOfLocations = mutableListOf<UserLocationData>()

    fun execute() : MutableList<UserLocationData> {

        val repetitiveCities = mutableListOf<String>()

        for(user in users) {
            if(!repetitiveCities.contains(user.city?.title)) {
                val result = geocoder.getFromLocationName(user.city?.title, 1)
                listOfLocations.add(UserLocationData(result[0].latitude, result[0].longitude, user.nameOfUser, user.city!!.title!!))
                repetitiveCities.add(user.city.title!!)
            }
        }

        return listOfLocations
    }
}