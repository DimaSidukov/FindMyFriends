package android.example.findmyfriends.data.source.local

import android.example.findmyfriends.data.source.local.database.dao.UserInfoDao
import android.example.findmyfriends.data.source.local.model.UserInfo
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val usersDao: UserInfoDao) : LocalSource {

    override suspend fun loadDataToDataBase(user: UserInfo) =  usersDao.insertUserInfo(user)

    override suspend fun loadAllDataToDataBase(users: List<UserInfo>) = usersDao.insertAllUsers(users)

    override suspend fun retrieveDataFromDataBase() : List<UserInfo> = usersDao.getAllUsers()

    override suspend fun clearDataBase() = usersDao.deleteAllUser()
}