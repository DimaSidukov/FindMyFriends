package android.example.findmyfriends.model.remote.vk.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "title")
    val title: String?
)