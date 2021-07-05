package android.example.findmyfriends.application

import android.app.Application
import android.example.findmyfriends.di.AppComponent
import android.example.findmyfriends.di.AppModule
import android.example.findmyfriends.di.DaggerAppComponent

open class FindMyFriendsApplication : Application() {

    lateinit var findMyFriendsComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        findMyFriendsComponent = initDagger(this)
    }

    private fun initDagger(app: FindMyFriendsApplication) : AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}