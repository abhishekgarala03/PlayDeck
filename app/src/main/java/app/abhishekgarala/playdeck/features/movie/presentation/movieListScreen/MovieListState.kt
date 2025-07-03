package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import androidx.paging.PagingData
import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import kotlinx.coroutines.flow.Flow

data class MovieListState(
    val genre : GenreEntity? = null,
    val movies : Flow<PagingData<MovieResultEntity>>? = null,
)