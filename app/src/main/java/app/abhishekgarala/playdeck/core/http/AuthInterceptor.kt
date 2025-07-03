package app.abhishekgarala.playdeck.core.http

import app.abhishekgarala.playdeck.core.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = Constants.API_READ_ACCESS_TOKEN
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}