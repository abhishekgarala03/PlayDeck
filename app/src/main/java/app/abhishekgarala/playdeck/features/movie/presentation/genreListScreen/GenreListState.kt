package app.abhishekgarala.playdeck.features.movie.presentation.genreListScreen

import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity

data class GenreListState(
    val isLoading: Boolean = false,
    val genresResult: Pair<Boolean, List<GenreEntity>?> = Pair(false, emptyList())
)