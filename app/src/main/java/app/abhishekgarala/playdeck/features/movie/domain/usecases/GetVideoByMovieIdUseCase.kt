package app.abhishekgarala.playdeck.features.movie.domain.usecases

import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.core.utils.responseFlow
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetVideoByMovieIdUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(
        movieID: Int
    ): Flow<Resource<VideoEntity>> {
        return responseFlow { repository.getMovieVideo(movieID) }
    }
}