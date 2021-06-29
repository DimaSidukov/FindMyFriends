package android.example.findmyfriends.vkdata.friendsinfo

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?
)