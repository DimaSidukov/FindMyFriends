package android.example.findmyfriends.model

import android.example.findmyfriends.geoservice.UserLocationData

var vkAccessToken: String = "token"
val VK_FRIENDS_URL: String = "https://api.vk.com/method/"
var requestFriendsVK: String = "friends.get?access_token="
var miscURL: String = "&order=name&fields=city,domain,photo_100%20&&v=5.131"
var locData : List<UserLocationData> = listOf()

fun setVKToken(token: String) {
    vkAccessToken = token
}

class Model {

}
