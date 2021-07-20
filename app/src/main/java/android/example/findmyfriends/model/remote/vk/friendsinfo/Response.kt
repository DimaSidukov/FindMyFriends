package android.example.findmyfriends.model.remote.vk.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @field:Json(name = "count")
    val count: Int? = null,
    @field:Json(name = "items")
    val items: List<Item>? = null
)