package android.example.findmyfriends.ui.map

import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.common.BasePresenter
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapPresenter @Inject constructor(private val array: ArrayList<UserLocationData>) : BasePresenter<MapView>() {

    fun showOneTheMap(map: GoogleMap) {
        lateinit var marker: LatLng

        Log.d("users are", array.toString())

        for (location in array) {
            marker = LatLng(location.latitude, location.longitude)
            map.addMarker(MarkerOptions().position(marker).title(location.city))
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }
}