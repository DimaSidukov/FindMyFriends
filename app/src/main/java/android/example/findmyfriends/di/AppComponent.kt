package android.example.findmyfriends.di

import android.example.findmyfriends.ui.mainactivity.MainActivity
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MainPresenterModule::class])
interface AppComponent {

    fun inject(target: MainActivity)
}