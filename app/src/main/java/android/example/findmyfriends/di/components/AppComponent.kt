package android.example.findmyfriends.di.components

import android.example.findmyfriends.di.modules.*
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import android.example.findmyfriends.repository.geocoder.GeocoderInterfaceHandler
import android.example.findmyfriends.repository.networkapi.RetrofitInterfaceHandler
import android.example.findmyfriends.ui.friendsactivity.FriendListActivity
import android.example.findmyfriends.ui.mainactivity.MainActivity
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, NetworkModule::class, DatabaseModule::class, GeocoderModule::class])
interface AppComponent {

    fun inject(target: MainActivity)
    fun inject(target: FriendListActivity)
    fun inject(target: MapsActivity)
}