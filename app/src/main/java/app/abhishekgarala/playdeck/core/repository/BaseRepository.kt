package app.abhishekgarala.playdeck.core.repository

import app.abhishekgarala.playdeck.core.utils.Constants.Companion.DEFAULT_ERROR_INTERNET_MESSAGE
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.DEFAULT_ERROR_MESSAGE
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import app.abhishekgarala.playdeck.core.utils.Resource
import app.abhishekgarala.playdeck.core.model.BaseResponse

abstract class BaseRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse: BaseResponse? = convertErrorBody(response.errorBody())
                    Timber.e("BASE_REPOSITORY: ${errorResponse?.message}")
                    Resource.Error(message = errorResponse?.message ?: DEFAULT_ERROR_MESSAGE)
                }

            } catch (e: HttpException) {
                Timber.e("BASE_REPOSITORY: ${e.message}")
                Resource.Error(message = e.message ?: DEFAULT_ERROR_MESSAGE)
            } catch (e: IOException) {
                Timber.e("BASE_REPOSITORY: ${e.message}")
                Resource.Error(DEFAULT_ERROR_INTERNET_MESSAGE)
            } catch (e: Exception) {
                Timber.e("BASE_REPOSITORY: ${e.message}")
                Resource.Error(message = DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): BaseResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(BaseResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}
