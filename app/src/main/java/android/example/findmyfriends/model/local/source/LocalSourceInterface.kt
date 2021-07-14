package android.example.findmyfriends.model.local.source

import android.example.findmyfriends.model.local.database.entity.UserInfo

interface LocalSourceInterface {

    suspend fun loadData(user: UserInfo)
    suspend fun retrieveData() : List<UserInfo>
    fun clearData()
}