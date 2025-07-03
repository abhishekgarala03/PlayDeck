package app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.abhishekgarala.playdeck.core.presentation.UiEvent
import app.abhishekgarala.playdeck.core.utils.Resource.Loading
import app.abhishekgarala.playdeck.core.utils.Resource.Success
import app.abhishekgarala.playdeck.core.viewModel.BaseViewModel
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetDetailMoviesByIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetReviewByMovieIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetVideoByMovieIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getDetailMovie: GetDetailMoviesByIdUseCase,
    private val getReviewers: GetReviewByMovieIdUseCase,
    private val getTrailers: GetVideoByMovieIdUseCase,
) : BaseViewModel<DetailMovieState, DetailMovieEvent>() {
    override fun defaultState(): DetailMovieState = DetailMovieState()

    override fun onEvent(event: DetailMovieEvent) {
        when (event) {
            is DetailMovieEvent.SetMovie -> onSetMovie(event.movie)
            is DetailMovieEvent.GetDetailMovie -> onGetDetailMovie()
            is DetailMovieEvent.GetReviewers -> onGetReviewers()
            is DetailMovieEvent.GetTrailers -> onGetTrailers()

            is DetailMovieEvent.NavigateBack -> sendEvent(
                UiEvent.NavigateBack
            )

            is DetailMovieEvent.CloseDialog -> commit {
                copy(
                    isDialogOpen  = false
                )
            }
            is DetailMovieEvent.OpenDialog -> commit {
                copy(
                    isDialogOpen  = true
                )
            }
        }
    }

    private fun onSetMovie(movie: MovieResultEntity?) = commit {
        copy(movie = movie)
    }

    private fun onGetReviewers() = asyncWithState {
        val reviews = movie?.id?.let { getReviewers(it) }?.cachedIn(viewModelScope)
        commit {
            copy(
                reviewers = reviews
            )
        }
    }

    private fun onGetTrailers() = asyncWithState {
        movie?.id?.let { getTrailers(it) }?.collect {result ->
            when (result) {
                is Loading -> commit {
                    copy(
                        isFetchingTrailers = true
                    )
                }

                is Success -> {
                    commit {
                        copy(
                            trailers = result.data.results.filter {
                                it.site == "YouTube" && it.type == "Trailer"
                            },
                            isFetchingTrailers = false
                        )
                    }
                    Timber.d("onGetTrailers: ${result.data}")
                }

                is Error -> {
                    commit {
                        copy(
                            trailers = null,
                            isFetchingTrailers = false
                        )
                    }
                    sendEvent(UiEvent.ShowSnackBar(result.message.toString()))
                    Timber.d("onGetTrailers Error: ${result.message}")
                }

                else -> {
                    commit {
                        copy(
                            trailers = null,
                            isFetchingTrailers = false
                        )
                    }
                    sendEvent(UiEvent.ShowSnackBar("onGetTrailers -> else"))
                    Timber.d("onGetTrailers Error: else")
                }
            }

        }
    }

    private fun onGetDetailMovie() = asyncWithState {
        movie?.id?.let { getDetailMovie(it) }?.collect { result ->
            when (result) {
                is Loading -> commit {
                    copy(
                        isFetchingDetail = true
                    )
                }

                is Success -> {
                    commit {
                        copy(
                            detailMovie = result.data,
                            isFetchingDetail = false
                        )
                    }
                    Timber.d("onGetDetailMovie: ${result.data}")
                }

                is Error -> {
                    commit {
                        copy(
                            detailMovie = null,
                            isFetchingDetail = false
                        )
                    }
                    sendEvent(UiEvent.ShowSnackBar(result.message.toString()))
                    Timber.d("onGetDetailMovie Error: ${result.message}")
                }

                else -> {
                    commit {
                        copy(
                            trailers = null,
                            isFetchingTrailers = false
                        )
                    }
                    sendEvent(UiEvent.ShowSnackBar("onGetDetailMovie -> else"))
                    Timber.d("onGetTrailers Error: else")
                }
            }

        }
    }

}