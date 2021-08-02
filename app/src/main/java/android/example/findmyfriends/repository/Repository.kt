package android.example.findmyfriends.repository

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData

interface Repository {

    suspend fun loadAllData(users: List<UserInfo>)
    suspend fun loadMapData(users: List<UserInfo>) : MutableList<UserLocationData>
    fun downloadData(token : String) : List<UserInfo>
    fun destroyDataBase()
}