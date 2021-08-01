package android.example.findmyfriends.repository

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData

interface Repository {

    suspend fun retrieveData() : List<UserInfo>
    fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
    suspend fun downloadData(token : String) : Boolean
}