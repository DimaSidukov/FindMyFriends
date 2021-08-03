package android.example.findmyfriends.data.source.remote.model.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetVkFriendsData(
    @field:Json(name = "response")
    val response: Response? = null
)