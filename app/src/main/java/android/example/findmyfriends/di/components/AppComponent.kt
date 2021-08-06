package android.example.findmyfriends.di.components

import android.example.findmyfriends.di.modules.*
import android.example.findmyfriends.ui.friends.FriendsPresenter
import android.example.findmyfriends.ui.main.MainPresenter
import android.example.findmyfriends.ui.map.MapPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,
    PresenterModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    GeocoderModule::class,
    RepositoryModule::class])
interface AppComponent {

    fun inject(presenter: MainPresenter)
    fun inject(activity: FriendsPresenter)
    fun inject(activity: MapPresenter)
}