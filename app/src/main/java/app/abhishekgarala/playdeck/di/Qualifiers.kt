package app.abhishekgarala.playdeck.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReqresRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TmdbRetrofit
