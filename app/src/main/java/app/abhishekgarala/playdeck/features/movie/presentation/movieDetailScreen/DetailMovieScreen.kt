@file:OptIn(ExperimentalMaterial3Api::class)

package app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import app.abhishekgarala.playdeck.core.utils.Constants
import app.abhishekgarala.playdeck.core.utils.extension.Empty
import app.abhishekgarala.playdeck.features.movie.presentation.movieDetailScreen.components.YoutubeScreen
import app.abhishekgarala.playdeck.ui.components.ErrorContainer
import app.abhishekgarala.playdeck.ui.components.LoadingContainer
import app.abhishekgarala.playdeck.ui.theme.PlayDeckTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieScreen(
    state: DetailMovieState,
    onEvent: (DetailMovieEvent) -> Unit,
) {
    val movie = state.detailMovie
    val rememberTrailer by lazy {
        state.trailers
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onEvent(DetailMovieEvent.GetDetailMovie)
        onEvent(DetailMovieEvent.GetReviewers)
        onEvent(DetailMovieEvent.GetTrailers)
    }
    ReviewsDialog(
        openDialog = state.isDialogOpen,
        onCloseDialog = {
            onEvent(DetailMovieEvent.CloseDialog)
        },
        state = state,
        onRetry = {
            onEvent(DetailMovieEvent.GetReviewers)
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.movie?.title.orEmpty(),
                        style = typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(DetailMovieEvent.NavigateBack)
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
        if (state.isFetchingDetail) {
            LoadingContainer()
        } else if (movie == null) {
            ErrorContainer {
                onEvent(DetailMovieEvent.GetDetailMovie)
            }
        } else {
            val painter = rememberAsyncImagePainter(
                Constants.BASE_IMAGE_URL + movie.posterPath,
            )
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    if (!movie.posterPath.isNullOrBlank()) {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Overview:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.overview.orEmpty(),
                        style = typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Release Date:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.releaseDate.orEmpty(),
                        style = typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Popularity:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.popularity?.toString().orEmpty(),
                        style = typography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Vote Average:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.voteAverage?.toString().orEmpty(),
                        style = typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Production Companies:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    movie.productionCompanies.forEach { company ->
                        Text(
                            text = "- ${company.name.orEmpty()}",
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Production Countries:",
                        style = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    movie.productionCountries.forEach { country ->
                        Text(
                            text = "- ${country.name.orEmpty()}",
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Spoken Languages:",
                        style = typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    movie.spokenLanguages.forEach { language ->
                        Text(
                            text = "- ${language.name.orEmpty()}",
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onEvent(DetailMovieEvent.OpenDialog) }) {
                        Text(
                            text = "See Reviewers",
                            style = typography.bodyLarge,
                        )
                    }
                }

                items(
                    rememberTrailer?.size ?: 0,
                ) {
                    rememberTrailer?.get(it).let { result ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = result?.name.orEmpty(),
                                style = typography.titleLarge,
                            )
                            IconButton(
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://www.youtube.com/watch?v=${result?.key.orEmpty()}")
                                    )
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = String.Empty
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        YoutubeScreen(videoId = result?.key.orEmpty())
                    }
                }

            }
        }
    }
}

@Composable
fun ReviewsDialog(
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    state: DetailMovieState,
    onRetry: () -> Unit,
) {
    val reviews = state.reviewers?.collectAsLazyPagingItems()
    if (openDialog) {
        Dialog(
            onDismissRequest = { onCloseDialog() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reviewers:",
                        style = typography.titleLarge,
                    )
                    IconButton(onClick = { onCloseDialog() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = String.Empty
                        )
                    }
                }

                when (reviews?.loadState?.refresh) {
                    is LoadState.Error -> {
                        ErrorContainer {
                            onRetry()
                        }
                    }

                    is LoadState.Loading -> {
                        LoadingContainer()
                    }

                    else -> {
                        if (reviews?.itemCount == 0) {
                            Text(
                                text = "No Reviewers",
                                style = typography.bodyLarge,
                            )
                        }
                        LazyColumn {
                            items(reviews?.itemCount ?: 0) {
                                reviews?.get(it)?.let { review ->
                                    val rating = review.authorDetails?.rating ?: 0

                                    Text(
                                        text = review.authorDetails?.username.orEmpty(),
                                        style = typography.titleLarge,
                                    )
                                    Text(
                                        text = "Rating: $rating",
                                        style = typography.bodyLarge.copy(
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = review.content.orEmpty(),
                                        style = typography.bodyLarge,
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp),
                                        thickness = 2.dp,
                                    )
                                }
                            }
                            when (reviews?.loadState?.append) {
                                is LoadState.Error -> {
                                    item {
                                        ErrorContainer {
                                            reviews.retry()
                                        }
                                    }
                                }

                                is LoadState.Loading -> {
                                    item {
                                        LoadingContainer()
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
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PlayDeckTheme {
        DetailMovieScreen(
            state = DetailMovieState(),
            onEvent = {}
        )
    }
}