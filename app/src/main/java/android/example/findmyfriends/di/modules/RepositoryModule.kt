package android.example.findmyfriends.di.modules

import android.example.findmyfriends.model.local.source.LocalSourceImpl
import android.example.findmyfriends.model.remote.source.RemoteSourceImpl
import android.example.findmyfriends.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalAndRemoteSource(loc: LocalSourceImpl, rem: RemoteSourceImpl) : RepositoryImpl = RepositoryImpl(loc, rem)
}