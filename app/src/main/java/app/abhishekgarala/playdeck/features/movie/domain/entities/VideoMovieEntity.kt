package app.abhishekgarala.playdeck.features.movie.domain.entities

data class VideoEntity(
    val id: Int?,
    val results: List<VideoResultEntity>
)

data class VideoResultEntity(
    val iso6391: String?,
    val iso31661: String?,
    val name: String?,
    val key: String?,
    val site: String?,
    val size: Int?,
    val type: String?,
    val official: Boolean?,
    val publishedAt: String?,
    val entityId: String?
)
