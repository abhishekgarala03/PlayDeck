package app.abhishekgarala.playdeck.features.movie.domain.entities

data class ReviewEntity(
    val id: Int?,
    val page: Int?,
    val results: List<ReviewResultEntity>,
    val totalPages: Int?,
    val totalResults: Int?
)

data class AuthorDetailsEntity(
    val name: String?,
    val username: String?,
    val avatarPath: String?,
    val rating: Int?
)

data class ReviewResultEntity(
    val author: String?,
    val authorDetails: AuthorDetailsEntity?,
    val content: String?,
    val createdAt: String?,
    val id: String?,
    val updatedAt: String?,
    val url: String?
)
