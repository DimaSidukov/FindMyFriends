package android.example.findmyfriends.di.modules

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.GoogleMap
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class GeocoderModule {

    @Provides
    @Singleton
    fun provideGeocoder(context: Context) : Geocoder = Geocoder(context, Locale.getDefault())
}