package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.abhishekgarala.playdeck.core.presentation.UiEvent
import app.abhishekgarala.playdeck.core.viewModel.BaseViewModel
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetMoviesUseCase
import app.abhishekgarala.playdeck.route.MovieNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
) : BaseViewModel<MovieListState, MovieListEvent>() {
    override fun defaultState(): MovieListState = MovieListState()

    private val _uiState = MutableStateFlow(MovieListState())
    val uiState: StateFlow<MovieListState> = _uiState.asStateFlow()

    val movies: Flow<PagingData<MovieResultEntity>> = getMovies.invoke()

    override fun onEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.GetMovies -> onGetMovies()
            is MovieListEvent.NavigateToDetail -> sendEvent(
                UiEvent.Navigate(
                    MovieNav.MovieDetail.route,
                    event.movie,
                    key = MovieNav.MovieDetail.route
                )
            )

            is MovieListEvent.NavigateBack -> sendEvent(
                UiEvent.NavigateBack
            )
        }
    }

    private fun onGetMovies() = asyncWithState {
        val movies = getMovies().cachedIn(viewModelScope)
        commit {
            copy(
                movies = movies
            )
        }
    }

}