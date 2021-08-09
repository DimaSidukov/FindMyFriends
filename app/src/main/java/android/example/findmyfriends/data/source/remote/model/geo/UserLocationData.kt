package android.example.findmyfriends.data.source.remote.model.geo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLocationData(
    val latitude: Double,
    val longitude: Double,
    val name: String?,
    val city: String?,
) : Parcelable
