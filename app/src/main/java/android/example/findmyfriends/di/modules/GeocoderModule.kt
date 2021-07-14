package android.example.findmyfriends.di.modules

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class GeocoderModule(val context: Context) {

    @Provides
    @Singleton
    fun provideGeocoder(context: Context) : Geocoder = Geocoder(context.applicationContext, Locale.getDefault())
}