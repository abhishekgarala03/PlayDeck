package app.abhishekgarala.playdeck.core.utils

class Constants {
    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Oops, something went wrong. Please try again"
        const val DEFAULT_ERROR_INTERNET_MESSAGE = "Check your internet connection"
        const val USER_NAV = "user_list"
        const val MV_NAV = "movie_list"
        const val MVD_NAV = "movie_detail"
        const val BEARER = "Bearer "
        const val LANGUAGE = "en-US"
        const val AUTH = "Authorization"
        const val LANG = "language"
        const val MOVIE_ID = "movie_id"
        const val PAGE = "page"
        const val USER_ENDPOINT = "api/users"
        const val AUTH_USER = "x-api-key"
        const val TRENDING_MOVIE_ENDPOINT = "trending/movie/day"
        const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
        const val MOVIE_VIDEOS_ENDPOINT = "movie/{movie_id}/videos"
        const val MOVIE_REVIEWS_ENDPOINT = "movie/{movie_id}/reviews"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_URL_USER = "https://reqres.in/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
        const val BUNDLE_ARG_KEY = "bundle_arg_key"
        const val TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YTZiNTdjNDczZWMwODIxN2U4NTU2ZGZhOWQwNWY5NCIsInN1YiI6IjY1MWJiYTIwMjIzYThiMDBhYjNkMWEwNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KzwyO2BmjIGyeHJCOu5XOAld_LiRAS92VTGTRQVCrA8"
        const val REQRES_API_KEY = "reqres-free-v1"
    }
}