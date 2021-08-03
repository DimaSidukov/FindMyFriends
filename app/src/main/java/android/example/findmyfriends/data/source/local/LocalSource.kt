package android.example.findmyfriends.data.source.local

import android.example.findmyfriends.data.source.local.model.UserInfo

interface LocalSource {

    suspend fun loadDataToDataBase(user: UserInfo)
    suspend fun loadAllDataToDataBase(users: List<UserInfo>)
    suspend fun retrieveDataFromDataBase() : List<UserInfo>
    fun destroyDataBase()
}