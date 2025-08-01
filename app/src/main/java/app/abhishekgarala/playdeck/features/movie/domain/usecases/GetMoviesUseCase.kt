package app.abhishekgarala.playdeck.features.movie.domain.usecases

import androidx.paging.PagingData
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(
    ): Flow<PagingData<MovieResultEntity>> {
        return repository.getMovieList()
    }
}