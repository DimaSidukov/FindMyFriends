package android.example.findmyfriends.presenters

import android.content.Context
import android.example.findmyfriends.presenters.common.PresenterBase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PresenterMap(context: Context) : PresenterBase(context) {

    fun showOneTheMap(map: GoogleMap) {
        lateinit var marker: LatLng

        for(location in accessLocationData()) {
            marker = LatLng(location.latitude, location.longitude)
            map.addMarker(MarkerOptions().position(marker).title(location.city))
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }
}