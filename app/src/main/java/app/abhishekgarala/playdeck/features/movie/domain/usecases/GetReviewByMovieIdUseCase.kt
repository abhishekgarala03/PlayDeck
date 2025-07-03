package app.abhishekgarala.playdeck.features.movie.domain.usecases

import androidx.paging.PagingData
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetReviewByMovieIdUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(
        movieID: Int,
    ): Flow<PagingData<ReviewResultEntity>> {
        return repository.getMovieReview(movieID)
    }
}