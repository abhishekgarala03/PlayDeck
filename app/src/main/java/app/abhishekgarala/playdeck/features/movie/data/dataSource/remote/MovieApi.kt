package app.abhishekgarala.playdeck.features.movie.data.dataSource.remote

import app.abhishekgarala.playdeck.BuildConfig
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.AUTH
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BEARER
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.LANG
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.LANGUAGE
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MOVIE_DETAILS_ENDPOINT
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MOVIE_ID
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MOVIE_REVIEWS_ENDPOINT
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.MOVIE_VIDEOS_ENDPOINT
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.PAGE
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.TRENDING_MOVIE_ENDPOINT
import app.abhishekgarala.playdeck.features.movie.data.model.response.DetailMovieResponse
import app.abhishekgarala.playdeck.features.movie.data.model.response.MovieResponse
import app.abhishekgarala.playdeck.features.movie.data.model.response.ReviewMovieResponse
import app.abhishekgarala.playdeck.features.movie.data.model.response.VideoMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(TRENDING_MOVIE_ENDPOINT)
    suspend fun getMovies(
        @Query(PAGE) page: Int,
        @Header(AUTH) authorization: String = BEARER + BuildConfig.TMDB_API_KEY
    ): retrofit2.Response<MovieResponse>

    @GET(MOVIE_DETAILS_ENDPOINT)
    suspend fun getDetailMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANG) language: String = LANGUAGE,
        @Header(AUTH) authorization: String = BEARER + BuildConfig.TMDB_API_KEY
    ): retrofit2.Response<DetailMovieResponse>

    @GET(MOVIE_VIDEOS_ENDPOINT)
    suspend fun getVideos(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANG) language: String = LANGUAGE,
        @Header(AUTH) authorization: String = BEARER + BuildConfig.TMDB_API_KEY
    ): retrofit2.Response<VideoMovieResponse>

    @GET(MOVIE_REVIEWS_ENDPOINT)
    suspend fun getReviews(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANG) language: String = LANGUAGE,
        @Query(PAGE) page: Int = 1,
        @Header(AUTH) authorization: String = BEARER + BuildConfig.TMDB_API_KEY
    ): retrofit2.Response<ReviewMovieResponse>

}