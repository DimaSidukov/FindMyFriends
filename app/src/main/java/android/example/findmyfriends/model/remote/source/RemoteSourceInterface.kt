package android.example.findmyfriends.model.remote.source

import android.example.findmyfriends.model.local.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData

interface RemoteSourceInterface {

    fun setRequest(token: String)
    fun createService()
    fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
}