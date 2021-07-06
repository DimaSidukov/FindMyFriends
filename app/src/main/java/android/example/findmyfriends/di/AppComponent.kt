package android.example.findmyfriends.di

import android.example.findmyfriends.ui.friendsactivity.FriendListActivity
import android.example.findmyfriends.ui.mainactivity.MainActivity
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(target: MainActivity)
    fun inject(target: FriendListActivity)
    fun inject(target: MapsActivity)
}