package app.abhishekgarala.playdeck.features.movie.data.dataSource.remote

import app.abhishekgarala.playdeck.BuildConfig
import app.abhishekgarala.playdeck.core.database.request.CreateUserRequest
import app.abhishekgarala.playdeck.core.database.response.CreateUserResponse
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.AUTH_USER
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.PAGE
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.REQRES_API_KEY
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.USER_ENDPOINT
import app.abhishekgarala.playdeck.features.movie.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET(USER_ENDPOINT)
    suspend fun getUsers(
        @Header(AUTH_USER) apiKey: String = REQRES_API_KEY,
        @Query(PAGE) page: Int
    ): Response<UserResponse>

    @POST(USER_ENDPOINT)
    suspend fun createUser(
        @Header(AUTH_USER) apiKey: String = REQRES_API_KEY,
        @Body request: CreateUserRequest
    ): Response<CreateUserResponse>

}