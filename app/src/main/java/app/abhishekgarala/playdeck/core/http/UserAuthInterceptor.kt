package app.abhishekgarala.playdeck.core.http

import app.abhishekgarala.playdeck.core.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class UserAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = Constants.REQRES_API_KEY
        val request = chain.request().newBuilder()
        request.addHeader("x-api-key", token)
        return chain.proceed(request.build())
    }
}