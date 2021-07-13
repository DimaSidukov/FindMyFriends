package android.example.findmyfriends.viewmodel.mappresenter

import android.content.Context
import android.example.findmyfriends.ui.mapsactivity.MapView
import android.example.findmyfriends.viewmodel.common.BasePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MapPresenter @Inject constructor (context: Context) : BasePresenter<MapView>(context) {

    fun showOneTheMap(map: GoogleMap) {
        lateinit var marker: LatLng

        for (location in accessLocationData()) {
            marker = LatLng(location.latitude, location.longitude)
            map.addMarker(MarkerOptions().position(marker).title(location.city))
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }
}