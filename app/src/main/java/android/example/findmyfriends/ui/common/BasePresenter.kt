package android.example.findmyfriends.ui.common

import android.example.findmyfriends.data.source.remote.api.NetworkAvailability
import com.omegar.mvp.MvpPresenter
import javax.inject.Inject

open class BasePresenter<view: BaseView> : MvpPresenter<view>() {

    @Inject
    lateinit var networkAvailability: NetworkAvailability

    fun isNetworkAvailable() = networkAvailability.isAvailable
}