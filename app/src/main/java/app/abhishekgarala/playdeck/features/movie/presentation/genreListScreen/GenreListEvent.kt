package app.abhishekgarala.playdeck.features.movie.presentation.genreListScreen

import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity

sealed interface GenreListEvent {
    data class NavigateToMovieList(
        val genre: GenreEntity
    ) : GenreListEvent
    data object GetGenres : GenreListEvent
}