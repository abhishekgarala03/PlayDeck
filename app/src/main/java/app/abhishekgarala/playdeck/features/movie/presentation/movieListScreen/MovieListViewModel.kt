package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.abhishekgarala.playdeck.core.presentation.UiEvent
import app.abhishekgarala.playdeck.core.viewModel.BaseViewModel
import app.abhishekgarala.playdeck.features.movie.data.model.request.MovieQuery
import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetMoviesUseCase
import app.abhishekgarala.playdeck.route.MovieNav
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
) : BaseViewModel<MovieListState, MovieListEvent>() {
    override fun defaultState(): MovieListState = MovieListState()

    override fun onEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.SetGenre -> onSetGenre(event.genre)
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

    private fun onSetGenre(genre: GenreEntity?) = commit {
        copy(genre = genre)
    }

    private fun onGetMovies() = asyncWithState {
        val query = MovieQuery(
            withGenres = genre?.id.toString()
        )
        val movies = getMovies(query).cachedIn(viewModelScope)
        commit {
            copy(
                movies = movies
            )
        }
    }

}