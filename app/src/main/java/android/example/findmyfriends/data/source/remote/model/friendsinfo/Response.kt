package android.example.findmyfriends.data.source.remote.model.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @field:Json(name = "count")
    val count: Int? = null,
    @field:Json(name = "items")
    val items: List<Item>? = null
)