package android.example.findmyfriends.repository.database

import android.example.findmyfriends.model.remote.database.entity.UserInfo

interface DataBaseInterface {

    suspend fun insertToDataBase(user: UserInfo)

    suspend fun getFromDataBase() : List<UserInfo>

    fun deleteDataBase()
}