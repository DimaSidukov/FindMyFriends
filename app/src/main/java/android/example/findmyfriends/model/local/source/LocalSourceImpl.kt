package android.example.findmyfriends.model.local.source

import android.example.findmyfriends.data.database.dao.UserInfoDao
import android.example.findmyfriends.data.database.database.AppDatabase
import android.example.findmyfriends.data.database.entity.UserInfo
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(val usersDao: UserInfoDao) : LocalSource {

    override suspend fun loadData(user: UserInfo) =  usersDao.insertUserInfo(user)

    override suspend fun loadAllData(users: List<UserInfo>) = usersDao.insertAllUsers(users)

    override suspend fun retrieveData() : List<UserInfo> = usersDao.getAllUsers()

    override fun destroyDataBase() {
        AppDatabase.destroyDataBase()
    }
}