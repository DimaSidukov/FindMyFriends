package android.example.findmyfriends.model.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val city: String,
    val photo_100: String
)
