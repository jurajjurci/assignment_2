package sk.jurci.core_network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import sk.jurci.core_network.BuildConfig

class AuthInterceptor : Interceptor {

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTHORIZATION_CONTENT = "Bearer %1s"
        private const val QUERY_API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(QUERY_API_KEY, BuildConfig.MOVIE_API_KEY)
            .build()

        val currentRequest = originalRequest
            .newBuilder()
            .url(newUrl)
        currentRequest.addHeader(
            AUTHORIZATION,
            AUTHORIZATION_CONTENT.format(BuildConfig.MOVIE_API_READ_ACCESS_TOKEN)
        )

        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}