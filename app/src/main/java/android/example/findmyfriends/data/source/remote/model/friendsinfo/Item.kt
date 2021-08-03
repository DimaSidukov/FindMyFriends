package android.example.findmyfriends.data.source.remote.model.friendsinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item (
    @field:Json(name = "first_name")
    val firstName: String? = null,
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "last_name")
    var lastName: String? = null,
    @field:Json(name = "can_access_closed")
    var canAccessClosed: Boolean? = null,
    @field:Json(name = "is_closed")
    var isClosed: Boolean? = null,
    @field:Json(name = "photo_100")
    var photo100: String? = null,
    @field:Json(name = "domain")
    var domain: String? = null,
    @field:Json(name = "city")
    var city: City? = null,
    @field:Json(name = "track_code")
    var trackCode: String? = null,
    @field:Json(name = "lists")
    var lists: List<Int>? = null,
    @field:Json(name = "deactivated")
    var deactivated: String? = null
)