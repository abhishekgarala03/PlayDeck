@file:OptIn(ExperimentalMaterial3Api::class)

package app.abhishekgarala.playdeck.features.movie.presentation.movieListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BASE_IMAGE_URL
import app.abhishekgarala.playdeck.core.utils.extension.Empty
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.ui.components.ErrorContainer
import app.abhishekgarala.playdeck.ui.components.LoadingContainer
import app.abhishekgarala.playdeck.ui.theme.PlayDeckTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MovieListState,
    onEvent: (MovieListEvent) -> Unit,
) {
    val movies = state.movies?.collectAsLazyPagingItems()


    LaunchedEffect(Unit) {
        onEvent(MovieListEvent.GetMovies)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie ${state.genre?.name ?: String.Empty}",
                        style = typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(MovieListEvent.NavigateBack)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = String.Empty
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when (movies?.loadState?.refresh) {
                is LoadState.Error -> {
                    ErrorContainer {
                        onEvent(MovieListEvent.GetMovies)
                    }
                }

                is LoadState.Loading -> {
                    LoadingContainer()
                }

                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            count = movies?.itemCount ?: 0,
                            key = movies?.itemKey { it.id.toString() },
                        ) {
                            movies?.get(it)?.let { movie ->
                                MovieCard(
                                    movie = movie,
                                    onClick = {
                                        onEvent(MovieListEvent.NavigateToDetail(movie))
                                    }
                                )
                            }
                        }
                        when (movies?.loadState?.append) {
                            is LoadState.Error -> {
                                item {
                                    ErrorContainer {
                                        movies.retry()
                                    }
                                }
                            }

                            is LoadState.Loading -> {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            else -> {
                                // DO NOTHING
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieCard(
    movie: MovieResultEntity,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(
        BASE_IMAGE_URL + movie.posterPath,
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!movie.posterPath.isNullOrBlank()) {
                Image(
                    painter = painter,
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = movie.title.orEmpty(),
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.releaseDate.orEmpty(),
                    style = typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    PlayDeckTheme {
        MovieListScreen(
            state = MovieListState(),
            onEvent = {}
        )
    }
}