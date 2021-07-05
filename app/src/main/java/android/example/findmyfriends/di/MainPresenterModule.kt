package android.example.findmyfriends.di

import android.content.Context
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainPresenterModule() {

    @Provides
    @Singleton
    fun provideMainPresenter(context: Context): MainPresenter = MainPresenter(context)
}