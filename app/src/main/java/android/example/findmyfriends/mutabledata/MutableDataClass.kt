package android.example.findmyfriends.mutabledata

import android.content.Context
import android.example.findmyfriends.geoservice.UserLocationData
import android.widget.Toast

var vkAccessToken = "token"
const val VK_FRIENDS_URL = "https://api.vk.com/method/"
var requestFriendsVK = "friends.get?access_token="
var miscURL = "&order=name&fields=city,domain,photo_100%20&&v=5.131"

var locData : List<UserLocationData> = listOf<UserLocationData>()

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}