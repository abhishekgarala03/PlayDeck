package app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen

import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity

sealed interface DetailMovieEvent {
    data class SetMovie(
        val movie: MovieResultEntity?
    ) : DetailMovieEvent

    data object GetReviewers : DetailMovieEvent
    data object GetTrailers : DetailMovieEvent
    data object GetDetailMovie : DetailMovieEvent
    data object NavigateBack : DetailMovieEvent
    data object OpenDialog : DetailMovieEvent
    data object CloseDialog : DetailMovieEvent
}