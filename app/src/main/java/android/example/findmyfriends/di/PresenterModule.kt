package android.example.findmyfriends.di

import android.content.Context
import android.example.findmyfriends.viewmodel.common.BasePresenter
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.example.findmyfriends.viewmodel.mappresenter.MapPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule() {

    @Provides
    @Singleton
    fun provideBasePresenter(context: Context) : BasePresenter = BasePresenter(context)

    @Provides
    @Singleton
    fun provideMainPresenter(context: Context): MainPresenter = MainPresenter(context)

    @Provides
    @Singleton
    fun provideMapPresenter(context: Context) : MapPresenter = MapPresenter(context)
}