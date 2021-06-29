package android.example.findmyfriends

import android.example.findmyfriends.databinding.ActivityMapsBinding
<<<<<<< HEAD
import android.example.findmyfriends.presenters.PresenterMap
=======
import android.example.findmyfriends.mutabledata.locData
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
<<<<<<< HEAD

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val presenter = PresenterMap(this)
=======
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
<<<<<<< HEAD
        map = googleMap

        map.setOnMapLoadedCallback {
            presenter.showOneTheMap(map)
        }
        map.animateCamera(CameraUpdateFactory.zoomTo(5.0f))
=======
        mMap = googleMap

        mMap.setOnMapLoadedCallback {
            lateinit var marker: LatLng

            for(location in locData) {
                marker = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(marker).title(location.city))
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        }
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 5.0f ) );
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}