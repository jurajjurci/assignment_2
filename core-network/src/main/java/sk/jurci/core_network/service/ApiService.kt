package sk.jurci.core_network.service

import retrofit2.http.GET
import retrofit2.http.Query
import sk.jurci.core_network.model.PopularMovieResponseDto

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularMovieResponseDto
}