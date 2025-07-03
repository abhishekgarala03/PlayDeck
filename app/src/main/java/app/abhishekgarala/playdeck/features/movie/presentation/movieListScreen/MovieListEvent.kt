package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity

sealed interface MovieListEvent {
    data class NavigateToDetail(
        val movie: MovieResultEntity
    ) : MovieListEvent

    data class SetGenre(
        val genre: GenreEntity?
    ) : MovieListEvent

    data object GetMovies : MovieListEvent

    data object NavigateBack : MovieListEvent
}