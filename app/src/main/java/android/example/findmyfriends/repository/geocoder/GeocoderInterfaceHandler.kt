package android.example.findmyfriends.repository.geocoder

import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.location.Geocoder
import java.util.*
import javax.inject.Inject

class GeocoderInterfaceHandler @Inject constructor(val geocoder: Geocoder) : GeocoderInterface {

    override fun buildList(users: List<UserInfo>): MutableList<UserLocationData> {

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