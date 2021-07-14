package android.example.findmyfriends.viewmodel.common

import android.content.Context
import android.example.findmyfriends.model.local.locData
import android.example.findmyfriends.model.local.miscURL
import android.example.findmyfriends.model.local.requestFriendsVK
import android.example.findmyfriends.model.local.vkAccessToken
import android.example.findmyfriends.ui.common.BaseView
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import moxy.MvpPresenter
import javax.inject.Inject

open class BasePresenter<view: BaseView> @Inject constructor (val context: Context) : MvpPresenter<view>() {

    fun accessVKToken() = vkAccessToken
    fun accessCurrentToken() = vkAccessToken
    fun accessRequestVK() = requestFriendsVK
    fun accessMiscURL() = miscURL
    fun accessLocationData() = locData

    fun isNetworkAvailable(): Boolean {
        val connectivityManager : ConnectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}