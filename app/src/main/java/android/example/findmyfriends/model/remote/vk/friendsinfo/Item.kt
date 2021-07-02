package android.example.findmyfriends.model.remote.vk.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @field:Json(name = "can_access_closed")
    val can_access_closed: Boolean?,
    @field:Json(name = "city")
    val city: City?,
    @field:Json(name = "domain")
    val domain: String?,
    @field:Json(name = "first_name")
    val first_name: String?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "is_closed")
    var is_closed: Boolean?,
    @field:Json(name = "last_name")
    val last_name: String?,
    @field:Json(name = "lists")
    val lists: List<Int>?,
    @field:Json(name = "photo_100")
    val photo_100: String?,
    @field:Json(name = "track_code")
    val track_code: String?
)