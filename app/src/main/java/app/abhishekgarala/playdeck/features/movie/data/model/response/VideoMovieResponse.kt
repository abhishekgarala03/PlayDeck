package app.abhishekgarala.playdeck.features.movie.data.model.response

import com.google.gson.annotations.SerializedName
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoResultEntity

data class VideoMovieResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("results") var results: ArrayList<ResultsVideoMovie> = arrayListOf()
) {
    fun toEntity(): VideoEntity {
        return VideoEntity(
            id = id,
            results = results.map { it.toEntity() }
        )
    }

    data class ResultsVideoMovie(
        @SerializedName("iso_639_1") var iso6391: String? = null,
        @SerializedName("iso_3166_1") var iso31661: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("key") var key: String? = null,
        @SerializedName("site") var site: String? = null,
        @SerializedName("size") var size: Int? = null,
        @SerializedName("type") var type: String? = null,
        @SerializedName("official") var official: Boolean? = null,
        @SerializedName("published_at") var publishedAt: String? = null,
        @SerializedName("id") var id: String? = null

    ) {
        fun toEntity(): VideoResultEntity {
            return VideoResultEntity(
                iso6391 = iso6391,
                iso31661 = iso31661,
                name = name,
                key = key,
                site = site,
                size = size,
                type = type,
                official = official,
                publishedAt = publishedAt,
                entityId = id
            )
        }
    }
}
