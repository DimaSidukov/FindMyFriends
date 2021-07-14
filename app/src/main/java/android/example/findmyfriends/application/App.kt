package android.example.findmyfriends.application

import android.app.Application
import android.example.findmyfriends.di.components.AppComponent
import android.example.findmyfriends.di.modules.AppModule
import android.example.findmyfriends.di.components.DaggerAppComponent

open class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: App) : AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}