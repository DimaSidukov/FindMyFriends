package android.example.findmyfriends.view.mapsactivity

import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.databinding.ActivityMapsBinding
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.view.common.BaseActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

class MapsActivity : BaseActivity(), OnMapReadyCallback, MapView {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    @Inject
    lateinit var presenter : MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App.appComponent.inject(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        val bundle = intent.extras
        val array = bundle?.getParcelableArrayList<UserLocationData>("arrayOfCities")

        map = googleMap

        map.setOnMapLoadedCallback {
            presenter.showOneTheMap(map, array!!)
        }
        map.animateCamera(CameraUpdateFactory.zoomTo(5.0f))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}