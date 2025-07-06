package app.abhishekgarala.playdeck.features.movie.domain.repositories

import androidx.paging.PagingData
import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.features.movie.domain.entities.DetailMovieEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity
import app.abhishekgarala.playdeck.features.movie.domain.entities.VideoEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieList(): Flow<PagingData<MovieResultEntity>>
    suspend fun getMovieDetail(movieId: Int): Resource<DetailMovieEntity>
    fun getMovieReview(movieId: Int): Flow<PagingData<ReviewResultEntity>>
    suspend fun getMovieVideo(movieId: Int): Resource<VideoEntity>
}