package android.example.findmyfriends.model.remote.geodata

import android.os.Parcel
import android.os.Parcelable

data class UserLocationData(
    val latitude: Double,
    val longitude: Double,
    val name: String?,
    val city: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(name)
        parcel.writeString(city)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserLocationData> {
        override fun createFromParcel(parcel: Parcel): UserLocationData {
            return UserLocationData(parcel)
        }

        override fun newArray(size: Int): Array<UserLocationData?> {
            return arrayOfNulls(size)
        }
    }
}
