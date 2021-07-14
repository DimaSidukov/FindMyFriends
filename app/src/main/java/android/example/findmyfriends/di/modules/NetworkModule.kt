package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.model.local.plain.VK_FRIENDS_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = VK_FRIENDS_URL
    }

    @Provides
    @Named(BASE_URL)
    fun provideBaseURL() = VK_FRIENDS_URL

    @Provides
    @Singleton
    fun provideRetrofitService(@Named(BASE_URL)url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}