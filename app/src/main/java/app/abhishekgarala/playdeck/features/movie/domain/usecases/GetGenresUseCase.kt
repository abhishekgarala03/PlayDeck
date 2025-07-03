package app.abhishekgarala.playdeck.features.movie.domain.usecases

import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.core.utils.responseFlow
import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: MovieRepository
) {
     operator fun invoke(): Flow<Resource<List<GenreEntity>>> {
        return responseFlow { repository.getMovieGenre() }
    }
}