package android.example.findmyfriends.vkdata.friendsinfo

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("can_access_closed")
    val can_access_closed: Boolean?,
    @SerializedName("city")
    val city: City?,
    @SerializedName("domain")
    val domain: String?,
    @SerializedName("first_name")
    val first_name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_closed")
    var is_closed: Boolean?,
    @SerializedName("last_name")
    val last_name: String?,
    @SerializedName("lists")
    val lists: List<Int>?,
    @SerializedName("photo_100")
    val photo_100: String?,
    @SerializedName("track_code")
    val track_code: String?,
    var nameOfUser: String
)