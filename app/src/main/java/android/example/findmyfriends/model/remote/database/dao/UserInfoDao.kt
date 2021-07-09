package android.example.findmyfriends.model.remote.database.dao

import android.example.findmyfriends.model.remote.database.entity.UserInfo
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)

    @Update
    suspend fun updateUserInfo(userInfo: UserInfo)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM UserInfo WHERE id = :id")
    fun getUserInfo(id: Int) : UserInfo

    @Query("SELECT * FROM UserInfo")
    fun getAllUsers() : List<UserInfo>

}