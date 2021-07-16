package android.example.findmyfriends.model.local.source

import android.example.findmyfriends.data.database.entity.UserInfo

interface LocalSource {

    suspend fun loadData(user: UserInfo)
    suspend fun loadAllData(users: List<UserInfo>)
    suspend fun retrieveData() : List<UserInfo>
    fun clearData()
}