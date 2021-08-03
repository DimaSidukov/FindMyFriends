package android.example.findmyfriends.di.components

import android.example.findmyfriends.di.modules.*
import android.example.findmyfriends.ui.friends.FriendListActivity
import android.example.findmyfriends.ui.main.MainActivity
import android.example.findmyfriends.ui.map.MapsActivity
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

    fun inject(activity: MainActivity)
    fun inject(activity: FriendListActivity)
    fun inject(activity: MapsActivity)
}