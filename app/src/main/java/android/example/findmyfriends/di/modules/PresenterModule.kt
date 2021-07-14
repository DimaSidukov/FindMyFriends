package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import android.example.findmyfriends.repository.geocoder.GeocoderInterfaceHandler
import android.example.findmyfriends.repository.networkapi.RetrofitInterfaceHandler
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.example.findmyfriends.viewmodel.mappresenter.MapPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideMainPresenter(context: Context): MainPresenter = MainPresenter(context)

    @Provides
    @Singleton
    fun provideFriendsPresenter(context: Context,
                                dbHandler : DataBaseInterfaceHandler,
                                retrofitHandler: RetrofitInterfaceHandler,
                                geocoder: GeocoderInterfaceHandler): FriendsPresenter = FriendsPresenter(context, dbHandler, retrofitHandler, geocoder)

    @Provides
    @Singleton
    fun provideMapPresenter(context: Context): MapPresenter = MapPresenter(context)
}