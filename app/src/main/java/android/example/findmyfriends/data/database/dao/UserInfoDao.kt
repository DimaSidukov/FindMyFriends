package android.example.findmyfriends.data.database.dao

import android.example.findmyfriends.data.database.entity.UserInfo
import androidx.room.*

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserInfo>)

    @Update
    suspend fun updateUserInfo(userInfo: UserInfo)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM UserInfo WHERE id = :id")
    suspend fun getUserInfo(id: Int) : UserInfo

    @Query("SELECT * FROM UserInfo")
    suspend fun getAllUsers() : List<UserInfo>

    @Query("DELETE FROM UserInfo")
    suspend fun deleteAllUser()

}