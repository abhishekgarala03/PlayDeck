package app.abhishekgarala.playdeck.features.movie.data.model.response

import com.google.gson.annotations.SerializedName
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity

data class MovieResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: ArrayList<ResultsMovie> = arrayListOf(),
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
) {
    fun toEntity(): MovieEntity {
        return MovieEntity(
            page = page,
            results = results.map { it.toResultsMovieEntity() },
            totalPages = totalPages,
            totalResults = totalResults
        )
    }
}

data class ResultsMovie(
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genre_ids") val genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id") val id: Int? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null
) {
    fun toResultsMovieEntity(): MovieResultEntity {
        return MovieResultEntity(
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
