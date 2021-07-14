package android.example.findmyfriends.di.modules

import android.example.findmyfriends.model.local.source.LocalSource
import android.example.findmyfriends.model.remote.source.RemoteSource
import android.example.findmyfriends.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalAndRemoteSource(loc: LocalSource, rem: RemoteSource) : Repository = Repository(loc, rem)
}