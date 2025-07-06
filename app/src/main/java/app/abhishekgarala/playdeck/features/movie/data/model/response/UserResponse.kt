package app.abhishekgarala.playdeck.features.movie.data.model.response

import app.abhishekgarala.playdeck.features.movie.domain.entities.UserEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.UserResultEntity
import com.google.gson.annotations.SerializedName

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserDto>
)

data class UserDto(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)
