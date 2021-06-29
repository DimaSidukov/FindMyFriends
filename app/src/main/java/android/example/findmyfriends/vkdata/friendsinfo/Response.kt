package android.example.findmyfriends.vkdata.friendsinfo

import com.google.gson.annotations.SerializedName


data class Response(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("items")
    val items: List<Item>?
)