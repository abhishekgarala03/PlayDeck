@file:OptIn(ExperimentalMaterial3Api::class)

package app.abhishekgarala.playdeck.features.movie.presentation.genreListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.abhishekgarala.playdeck.core.utils.extension.Empty
import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.ui.components.ErrorContainer
import app.abhishekgarala.playdeck.ui.theme.PlayDeckTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreListScreen(
    state: GenreListState,
    onEvent: (GenreListEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(GenreListEvent.GetGenres)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Choose your genre",
                        style = typography.titleLarge,
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (!state.genresResult.first && state.genresResult.second == null) {
                ErrorContainer {
                    onEvent(GenreListEvent.GetGenres)
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(state.genresResult.second?.size ?: 0) {
                        state.genresResult.second?.get(it)?.let { genre ->
                            GenreItem(
                                genre = genre,
                                onClick = {
                                    onEvent(GenreListEvent.NavigateToMovieList(genre))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreItem(
    genre: GenreEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Card(onClick = onClick) {
            Text(
                genre.name ?: String.Empty,
                style = typography.labelSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    PlayDeckTheme {
        GenreListScreen(
            state = GenreListState(),
            onEvent = {}
        )
    }
}