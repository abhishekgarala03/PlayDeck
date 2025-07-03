package app.abhishekgarala.playdeck.features.movie.data.model.response

import com.google.gson.annotations.SerializedName
import app.abhishekgarala.playdeck.features.movie.domain.entities.AuthorDetailsEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity

data class ReviewMovieResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<ResultsReviewMovie> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
) {
    fun toEntity(): ReviewEntity {
        return ReviewEntity(
            id = id,
            page = page,
            results = results.map { it.toEntity() },
            totalPages = totalPages,
            totalResults = totalResults
        )
    }
}

data class AuthorDetails(

    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("avatar_path") var avatarPath: String? = null,
    @SerializedName("rating") var rating: Int? = null

) {
    fun toEntity(): AuthorDetailsEntity {
        return AuthorDetailsEntity(
            name = name,
            username = username,
            avatarPath = avatarPath,
            rating = rating
        )
    }
}

data class ResultsReviewMovie(

    @SerializedName("author") var author: String? = null,
    @SerializedName("author_details") var authorDetails: AuthorDetails? = AuthorDetails(),
    @SerializedName("content") var content: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("url") var url: String? = null

) {
    fun toEntity(): ReviewResultEntity {
        return ReviewResultEntity(
            author = author,
            authorDetails = authorDetails?.toEntity(),
            content = content,
            createdAt = createdAt,
            id = id,
            updatedAt = updatedAt,
            url = url
        )
    }
}