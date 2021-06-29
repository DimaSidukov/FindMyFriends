package android.example.findmyfriends.vkdata.friendsinfo

import com.google.gson.annotations.SerializedName

data class GetVkFriendsData(
    @SerializedName("response")
    val response: Response?
)