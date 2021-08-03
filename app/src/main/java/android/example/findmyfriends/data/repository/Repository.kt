package android.example.findmyfriends.data.repository

import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData

interface Repository {

    suspend fun loadAllDataToDataBase(users: List<UserInfo>)
    suspend fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
    fun downloadData(token : String) : List<UserInfo>
    fun destroyDataBase()
}