package android.example.findmyfriends.ui.map

import android.example.findmyfriends.R
import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.databinding.ActivityMapsBinding
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.common.BaseActivity
import android.example.findmyfriends.ui.friends.FriendsPresenter
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.omegar.mvp.presenter.InjectPresenter
import com.omegar.mvp.presenter.PresenterType
import com.omegar.mvp.presenter.ProvidePresenter
import javax.inject.Inject

class MapsActivity : BaseActivity(), OnMapReadyCallback, MapView {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    @InjectPresenter
    lateinit var presenter : MapPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun providePresenter(): MapPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FindMyFriendsApplication.appComponent.inject(this)

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