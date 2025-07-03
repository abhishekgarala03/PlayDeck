package app.abhishekgarala.playdeck.features.movie.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.abhishekgarala.playdeck.core.repository.BaseRepository
import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.features.movie.data.dataSource.MoviesPagingSource
import app.abhishekgarala.playdeck.features.movie.data.dataSource.ReviewersPagingSource
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.MovieApi
import app.abhishekgarala.playdeck.features.movie.data.model.request.MovieQuery
import app.abhishekgarala.playdeck.features.movie.domain.entities.DetailMovieEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.GenreEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoEntity
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository,
    BaseRepository() {
    override fun getMovieList(query: MovieQuery): Flow<PagingData<MovieResultEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    api = api,
                    query = query
                )
            }
        ).flow
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<DetailMovieEntity> {
        return when (val res = safeApiCall { api.getDetailMovie(movieId) }) {
            is Resource.Success -> Resource.Success(res.data.toEntity())
            is Resource.Error -> Resource.Error(res.message)
            is Resource.Loading -> Resource.Loading
        }
    }

    override fun getMovieReview(movieId: Int): Flow<PagingData<ReviewResultEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewersPagingSource(
                    api = api,
                    movieId = movieId
                )
            }
        ).flow
    }

    override suspend fun getMovieVideo(movieId: Int): Resource<VideoEntity> {
        return when (val res = safeApiCall { api.getVideos(movieId) }) {
            is Resource.Success -> Resource.Success(res.data.toEntity())
            is Resource.Error -> Resource.Error(res.message)
            is Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun getMovieGenre(): Resource<List<GenreEntity>> {
        return when (val res = safeApiCall { api.getGenres() }) {
            is Resource.Success -> Resource.Success(res.data.gendre?.map { it.toEntity() }
                ?: emptyList())

            is Resource.Error -> Resource.Error(res.message)
            is Resource.Loading -> Resource.Loading
        }
    }

}