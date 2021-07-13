package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context) :AppDatabase = AppDatabase.getAppDataBase(context)!!

    @Provides
    @Singleton
    fun provideUserInfoDao(context: Context) : UserInfoDao = provideDataBase(context).userInfoDao()

    @Provides
    @Singleton
    fun provideDataBaseHandler() : DataBaseInterfaceHandler = DataBaseInterfaceHandler()
}