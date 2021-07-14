package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.model.local.database.dao.UserInfoDao
import android.example.findmyfriends.model.local.database.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Context) {

    @Provides
    @Singleton
    fun provideDataBase(context: Context) : AppDatabase = AppDatabase.getAppDataBase(context.applicationContext)!!

    @Provides
    @Singleton
    fun provideUserInfoDao(context: Context) : UserInfoDao = provideDataBase(context.applicationContext).userInfoDao()
}