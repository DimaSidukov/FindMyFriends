package android.example.findmyfriends.model.local.source

import android.example.findmyfriends.model.local.database.dao.UserInfoDao
import android.example.findmyfriends.model.local.database.database.AppDatabase
import android.example.findmyfriends.model.local.database.entity.UserInfo
import android.example.findmyfriends.model.local.plain.userList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalSource @Inject constructor(val usersDao: UserInfoDao) : LocalSourceInterface {

    override suspend fun loadData(user: UserInfo) =  usersDao.insertUserInfo(user)

    override suspend fun retrieveData() : List<UserInfo> = usersDao.getAllUsers()

    override fun clearData() {
        AppDatabase.destroyDataBase()
        userList = listOf()
    }
}