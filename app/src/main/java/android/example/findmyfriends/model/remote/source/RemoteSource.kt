package android.example.findmyfriends.model.remote.source

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData

interface RemoteSource {

    fun getResponse(token: String, url: String) : List<UserInfo>
    fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
}