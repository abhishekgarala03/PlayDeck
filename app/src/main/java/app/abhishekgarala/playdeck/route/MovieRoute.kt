package app.abhishekgarala.playdeck.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import app.abhishekgarala.playdeck.core.presentation.BaseScreenWrapper
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MVD_NAV
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MV_NAV
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.USER_NAV
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen.DetailMovieEvent
import app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen.DetailMovieScreen
import app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen.DetailMovieViewModel
import app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen.MovieListScreen
import app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen.MovieListViewModel
import app.abhishekgarala.playdeck.features.movie.presentation.userListScreen.UserListScreen

fun NavGraphBuilder.movieNavigation(
    navController: NavHostController,
) {
    navigation(
        route = Graph.MOVIE,
        startDestination = MovieNav.UserList.route
    ) {
        composable(route = MovieNav.UserList.route) {
            UserListScreen(
                onUserClick = { user ->
                    navController.navigate(MovieNav.MovieList.route)
                }
            )
        }
        composable(route = MovieNav.MovieList.route) {
            val viewModel = hiltViewModel<MovieListViewModel>()
            val state by viewModel.state.collectAsState()
            BaseScreenWrapper(navController = navController, viewModel = viewModel) {
                MovieListScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                )
            }
        }
        composable(route = MovieNav.MovieDetail.route) {
            val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
            val data = savedStateHandle?.get<MovieResultEntity>(MovieNav.MovieDetail.route)
            val viewModel = hiltViewModel<DetailMovieViewModel>()
            val state by viewModel.state.collectAsState()
            viewModel.onEvent(DetailMovieEvent.SetMovie(data))
            BaseScreenWrapper(navController = navController, viewModel = viewModel) {
                DetailMovieScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

sealed class MovieNav(val route: String) {
    data object UserList : MovieNav(USER_NAV)
    data object MovieList : MovieNav(MV_NAV)
    data object MovieDetail : MovieNav(MVD_NAV)
}