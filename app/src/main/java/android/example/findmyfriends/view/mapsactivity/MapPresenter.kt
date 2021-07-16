package android.example.findmyfriends.view.mapsactivity

import android.content.Context
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.view.common.BasePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MapPresenter @Inject constructor (context: Context) : BasePresenter<MapView>(context) {

    fun showOneTheMap(map: GoogleMap, array: ArrayList<UserLocationData>) {
        lateinit var marker: LatLng

        for (location in array) {
            marker = LatLng(location.latitude, location.longitude)
            map.addMarker(MarkerOptions().position(marker).title(location.city))
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }
}