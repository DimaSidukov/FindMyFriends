package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideUserInfoDao(context: Context) : UserInfoDao = AppDatabase.getAppDataBase(context)?.userInfoDao()!!
}