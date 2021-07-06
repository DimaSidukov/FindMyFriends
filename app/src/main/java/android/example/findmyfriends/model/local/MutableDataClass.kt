package android.example.findmyfriends.model.local

import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.util.SparseBooleanArray
import dagger.Provides
import javax.inject.Singleton

var textViewText : String? = null

var vkAccessToken: String = "token"
const val VK_FRIENDS_URL: String = "https://api.vk.com/method/"
var requestFriendsVK: String = "friends.get?access_token="
var miscURL: String = "&order=name&fields=city,domain,photo_100%20&&v=5.131"

var locData : List<UserLocationData> = listOf()

var isDbCreated = false

lateinit var array: SparseBooleanArray

fun setVKToken(token: String) {
    vkAccessToken = token
}