package app.abhishekgarala.playdeck.core.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val isPendingSync: Boolean = false
) : Parcelable