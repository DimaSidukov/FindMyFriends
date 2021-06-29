package android.example.findmyfriends.presenters.common

import android.content.Context
import android.example.findmyfriends.model.*
import android.widget.Toast

open class PresenterBase(private val context: Context) {

    val model = Model()

    fun accessVKToken() = vkAccessToken
    fun accessCurrentToken() = vkAccessToken
    fun accessBaseUrl() = VK_FRIENDS_URL
    fun accessRequestVK() = requestFriendsVK
    fun accessMiscURL() = miscURL
    fun accessLocationData() = locData


    fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}