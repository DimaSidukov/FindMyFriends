package android.example.findmyfriends.model.remote.vk.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetVkFriendsData(
    @field:Json(name = "response")
    val response: Response?
)