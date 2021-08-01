package android.example.findmyfriends.data.database.database

import android.content.Context
import android.example.findmyfriends.data.database.dao.UserInfoDao
import android.example.findmyfriends.data.database.entity.UserInfo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserInfo::class], version=2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "UserInfoDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}