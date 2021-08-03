package android.example.findmyfriends.data.source.remote.model.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "title")
    val title: String? = null
)