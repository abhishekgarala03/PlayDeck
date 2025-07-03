package app.abhishekgarala.playdeck.features.movie.data.model.request

import com.google.gson.annotations.SerializedName

data class MovieQuery(
    @SerializedName("include_adult")
    val includeAdult: Boolean = false,
    @SerializedName("include_video")
    val includeVideo: Boolean = true,
    @SerializedName("language")
    val language: String = "en-US",
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("sort_by")
    val sortBy: String = "popularity.desc",
    @SerializedName("with_genres")
    val withGenres: String,
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "include_adult" to includeAdult.toString(),
            "include_video" to includeVideo.toString(),
            "language" to language,
            "page" to page.toString(),
            "sort_by" to sortBy,
            "with_genres" to withGenres
        )
    }
}
