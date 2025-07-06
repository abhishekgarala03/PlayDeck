package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity

sealed interface MovieListEvent {
    data class NavigateToDetail(
        val movie: MovieResultEntity
    ) : MovieListEvent

    data object GetMovies : MovieListEvent

    data object NavigateBack : MovieListEvent
}