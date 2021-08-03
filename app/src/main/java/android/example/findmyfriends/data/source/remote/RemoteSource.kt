package android.example.findmyfriends.data.source.remote

import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData

interface RemoteSource {

    fun getResponse(token: String) : List<UserInfo>
    fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
}