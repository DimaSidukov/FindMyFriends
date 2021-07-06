package android.example.findmyfriends.viewmodel.common

import android.content.Context
import android.example.findmyfriends.model.local.*
import android.net.ConnectivityManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BasePresenter(private val context: Context) {

    fun accessVKToken() = vkAccessToken
    fun accessCurrentToken() = vkAccessToken
    fun accessRequestVK() = requestFriendsVK
    fun accessMiscURL() = miscURL
    fun accessLocationData() = locData


    fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager : ConnectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}