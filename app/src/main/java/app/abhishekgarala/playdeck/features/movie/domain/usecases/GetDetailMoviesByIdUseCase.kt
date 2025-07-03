package app.abhishekgarala.playdeck.features.movie.domain.usecases

import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.core.utils.responseFlow
import app.abhishekgarala.playdeck.features.movie.domain.entities.DetailMovieEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetDetailMoviesByIdUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(
        movieID: Int
    ): Flow<Resource<DetailMovieEntity>> {
        return responseFlow { repository.getMovieDetail(movieID) }
    }
}