package android.example.findmyfriends.di.modules

import android.example.findmyfriends.data.source.local.LocalSourceImpl
import android.example.findmyfriends.data.source.remote.RemoteSourceImpl
import android.example.findmyfriends.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalAndRemoteSource(loc: LocalSourceImpl, rem: RemoteSourceImpl) : RepositoryImpl = RepositoryImpl(loc, rem)
}