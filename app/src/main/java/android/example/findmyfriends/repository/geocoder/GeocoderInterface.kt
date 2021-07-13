package android.example.findmyfriends.repository.geocoder

import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData

interface GeocoderInterface {

    fun buildList(users: List<UserInfo>) : MutableList<UserLocationData>
}