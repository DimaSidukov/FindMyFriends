package android.example.findmyfriends.di.modules

import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import android.example.findmyfriends.repository.geocoder.GeocoderInterfaceHandler
import android.example.findmyfriends.repository.networkapi.RetrofitInterfaceHandler
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDataBaseInterfaceHandler(usersDao: UserInfoDao) = DataBaseInterfaceHandler(usersDao)

    @Provides
    @Singleton
    fun provideRetrofitInterfaceHandler(retrofit: Retrofit, usersDao: DataBaseInterfaceHandler ) = RetrofitInterfaceHandler(retrofit, usersDao)

    @Provides
    @Singleton
    fun provideGeocoderInterfaceHandler(geocoder: Geocoder) = GeocoderInterfaceHandler(geocoder)
}