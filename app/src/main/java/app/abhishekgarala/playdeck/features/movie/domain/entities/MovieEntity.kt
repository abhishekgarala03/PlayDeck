package app.abhishekgarala.playdeck.features.movie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieEntity(
    val page: Int?,
    val results: List<MovieResultEntity>,
    val totalPages: Int?,
    val totalResults: Int?
)

@Parcelize
data class MovieResultEntity(
    val id: Int?,
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable
