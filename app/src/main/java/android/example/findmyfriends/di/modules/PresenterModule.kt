package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.repository.RepositoryImpl
import android.example.findmyfriends.view.friendsactivity.FriendsPresenter
import android.example.findmyfriends.view.mainactivity.MainPresenter
import android.example.findmyfriends.view.mapsactivity.MapPresenter
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
    fun provideFriendsPresenter(context: Context, repositoryImpl: RepositoryImpl) : FriendsPresenter =
        FriendsPresenter(context.applicationContext, repositoryImpl)

    @Provides
    @Singleton
    fun provideMapPresenter(context: Context): MapPresenter = MapPresenter(context.applicationContext)
}