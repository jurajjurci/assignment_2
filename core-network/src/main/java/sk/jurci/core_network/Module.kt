package sk.jurci.core_network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import sk.jurci.core_network.interceptor.AuthInterceptor
import sk.jurci.core_network.service.ApiService
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    internal fun providesApiService(): ApiService {
        val json = Json { ignoreUnknownKeys = true }
        val mediaType = "application/json; charset=UTF8".toMediaType()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
            .create(ApiService::class.java)
    }
}