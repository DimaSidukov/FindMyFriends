package android.example.findmyfriends.ui.map

import android.example.findmyfriends.R
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.databinding.ActivityMapsBinding
import android.example.findmyfriends.ui.common.BaseActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.omegar.mvp.presenter.InjectPresenter
import com.omegar.mvp.presenter.ProvidePresenter

class MapsActivity : BaseActivity(), OnMapReadyCallback, MapView {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    @InjectPresenter
    lateinit var presenter : MapPresenter

    @ProvidePresenter
    fun providePresenter() = MapPresenter(intent.extras?.getParcelableArrayList("arrayOfCities")!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}