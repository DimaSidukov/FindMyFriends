package android.example.findmyfriends.di.modules

import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.example.findmyfriends.viewmodel.mappresenter.MapPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule() {

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter = MainPresenter()

    @Provides
    @Singleton
    fun provideFriendsPresenter(): FriendsPresenter = FriendsPresenter()

    @Provides
    @Singleton
    fun provideMapPresenter(): MapPresenter = MapPresenter()
}