package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.friends.FriendsPresenter
import android.example.findmyfriends.ui.main.MainPresenter
import android.example.findmyfriends.ui.map.MapPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule(val context: Context) {

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter = MainPresenter()

    @Provides
    @Singleton
    fun provideFriendsPresenter(token: String) : FriendsPresenter = FriendsPresenter(token)

    @Provides
    @Singleton
    fun provideMapPresenter(array: ArrayList<UserLocationData>): MapPresenter = MapPresenter(array)
}