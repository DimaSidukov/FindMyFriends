package android.example.findmyfriends.repository.database

import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.model.remote.database.entity.UserInfo
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DataBaseInterfaceHandler : DataBaseInterface {

    @Inject
    lateinit var usersDao: UserInfoDao

    override suspend fun insertToDataBase(user: UserInfo) = coroutineScope {
            usersDao.insertUserInfo(user)
    }

    override suspend fun getFromDataBase() : List<UserInfo> = coroutineScope {
            return@coroutineScope usersDao.getAllUsers()
    }

    override fun deleteDataBase() {
        AppDatabase.destroyDataBase()
    }
}