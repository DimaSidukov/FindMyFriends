package android.example.findmyfriends.di.modules

import android.content.Context
import android.example.findmyfriends.data.source.remote.api.NetworkAvailability
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import android.content.res.Resources
import android.example.findmyfriends.R

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.vk.com/method/"
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionStatus(context: Context): NetworkAvailability {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        val result = activeNetworkInfo != null && activeNetworkInfo.isConnected
        return NetworkAvailability(result)
    }

    @Provides
    @Named(BASE_URL)
    fun provideBaseURL() = "https://api.vk.com/method/"

    @Provides
    @Singleton
    fun provideRetrofitService(@Named(BASE_URL)url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}