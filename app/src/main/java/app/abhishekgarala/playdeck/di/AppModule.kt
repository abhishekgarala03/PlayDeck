package app.abhishekgarala.playdeck.di

import app.abhishekgarala.playdeck.core.http.AuthInterceptor
import app.abhishekgarala.playdeck.core.http.OkHttpClientFactory
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BASE_URL
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.MovieApi
import app.abhishekgarala.playdeck.features.movie.data.repositories.MovieRepositoryImpl
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetDetailMoviesByIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetGenresUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetMoviesUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetReviewByMovieIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetVideoByMovieIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClientFactory.create(
            interceptors = arrayOf(
                AuthInterceptor()
            ),
        )
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieAPI(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetGenresUseCase(repository: MovieRepository): GetGenresUseCase {
        return GetGenresUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailMovieUseCase(repository: MovieRepository): GetDetailMoviesByIdUseCase {
        return GetDetailMoviesByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsUseCase(repository: MovieRepository): GetReviewByMovieIdUseCase {
        return GetReviewByMovieIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetVideosUseCase(repository: MovieRepository): GetVideoByMovieIdUseCase {
        return GetVideoByMovieIdUseCase(repository)
    }

}