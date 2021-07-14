package android.example.findmyfriends.application

import android.app.Activity
import android.app.Application
import android.example.findmyfriends.di.components.AppComponent
import android.example.findmyfriends.di.components.DaggerAppComponent
import android.example.findmyfriends.di.modules.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun androidInjector(): AndroidInjector<Any>? = dispatchingAndroidInjector as AndroidInjector<Any>

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(applicationContext))
            .geocoderModule(GeocoderModule(applicationContext))
            .repositoryModule(RepositoryModule())
            .presenterModule(PresenterModule(applicationContext))
            .build()

        super.onCreate()
    }
}