package android.example.findmyfriends.model.local.source

import android.example.findmyfriends.model.local.database.dao.UserInfoDao
import android.example.findmyfriends.model.local.database.database.AppDatabase
import android.example.findmyfriends.model.local.database.entity.UserInfo
import android.example.findmyfriends.model.local.plain.userList
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class LocalSource @Inject constructor(val usersDao: UserInfoDao) : LocalSourceInterface {

    override suspend fun loadData(user: UserInfo) = coroutineScope {
        usersDao.insertUserInfo(user)
    }

    override suspend fun retrieveData() : List<UserInfo> = coroutineScope {
        return@coroutineScope usersDao.getAllUsers()
    }

    override fun clearData() {
        AppDatabase.destroyDataBase()
        userList = listOf()
    }
}