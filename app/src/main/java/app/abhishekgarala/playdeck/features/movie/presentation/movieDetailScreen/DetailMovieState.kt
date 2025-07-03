package app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen

import androidx.paging.PagingData
import app.abhishekgarala.playdeck.features.movie.domain.entities.DetailMovieEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoResultEntity
import kotlinx.coroutines.flow.Flow

data class DetailMovieState(
    val isDialogOpen: Boolean = false,
    val movie : MovieResultEntity? = null,
    val isFetchingDetail: Boolean = false,
    val isFetchingReviewers: Boolean = false,
    val isFetchingTrailers: Boolean = false,
    val detailMovie : DetailMovieEntity? = null,
    val reviewers : Flow<PagingData<ReviewResultEntity>>? = null,
    val trailers : List<VideoResultEntity>? = null,
)