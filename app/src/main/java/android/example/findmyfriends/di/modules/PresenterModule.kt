package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.data.repository.RepositoryImpl
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
    fun provideMainPresenter(context: Context): MainPresenter = MainPresenter(context.applicationContext)

    @Provides
    @Singleton
    fun provideFriendsPresenter(context: Context, repositoryImpl: RepositoryImpl) : FriendsPresenter =
        FriendsPresenter(context.applicationContext, repositoryImpl)

    @Provides
    @Singleton
    fun provideMapPresenter(context: Context): MapPresenter = MapPresenter(context.applicationContext)
}