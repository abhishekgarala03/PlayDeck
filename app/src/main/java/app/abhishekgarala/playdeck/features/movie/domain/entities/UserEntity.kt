package app.abhishekgarala.playdeck.features.movie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserEntity(
    var page: Int?,
    var perPage: Int?,
    var total: Int?,
    var totalPages: Int?,
    var data: List<UserResultEntity>
)

@Parcelize
data class UserResultEntity(
    var id: Int? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null
) : Parcelable
