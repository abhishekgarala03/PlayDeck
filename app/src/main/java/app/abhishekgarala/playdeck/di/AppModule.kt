package app.abhishekgarala.playdeck.di

import android.content.Context
import androidx.work.WorkManager
import app.abhishekgarala.playdeck.core.observer.NetworkConnectivityObserver
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BASE_URL
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BASE_URL_USER
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.MovieApi
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.UserApi
import app.abhishekgarala.playdeck.features.movie.data.repositories.MovieRepositoryImpl
import app.abhishekgarala.playdeck.features.movie.domain.repositories.MovieRepository
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetDetailMoviesByIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetMoviesUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetReviewByMovieIdUseCase
import app.abhishekgarala.playdeck.features.movie.domain.usecases.GetVideoByMovieIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @ReqresRetrofit
    fun provideReqresRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_USER)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @TmdbRetrofit
    fun provideTmdbRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(@ReqresRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieAPI(@TmdbRetrofit retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
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


    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}