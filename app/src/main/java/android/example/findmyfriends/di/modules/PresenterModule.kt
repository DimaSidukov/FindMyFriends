package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.repository.Repository
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.example.findmyfriends.viewmodel.mappresenter.MapPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule(val context: Context) {

    @Provides
    @Singleton
    fun provideMainPresenter(context: Context): MainPresenter = MainPresenter(context.applicationContext)

    @Provides
    @Singleton
    fun provideFriendsPresenter(context: Context, repository: Repository) : FriendsPresenter =
        FriendsPresenter(context.applicationContext, repository)

    @Provides
    @Singleton
    fun provideMapPresenter(context: Context): MapPresenter = MapPresenter(context.applicationContext)
}