package android.example.findmyfriends.ui.mapsactivity

import android.example.findmyfriends.R
import android.example.findmyfriends.application.FindMyFriendsApplication
import android.example.findmyfriends.databinding.ActivityMapsBinding
import android.example.findmyfriends.viewmodel.mappresenter.MapPresenter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapView {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    @Inject
    lateinit var presenter : MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as FindMyFriendsApplication).findMyFriendsComponent.inject(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap

        map.setOnMapLoadedCallback {
            presenter.showOneTheMap(map)
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