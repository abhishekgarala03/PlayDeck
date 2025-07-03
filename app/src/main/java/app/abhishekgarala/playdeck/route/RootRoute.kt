package app.abhishekgarala.playdeck.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MOVIE,
    ) {
        movieNavigation(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MOVIE = "movie_graph"
}