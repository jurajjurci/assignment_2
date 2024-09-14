package sk.jurci.core_network

import retrofit2.http.GET
import retrofit2.http.Query
import sk.jurci.core_network.model.MovieEntity

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): List<MovieEntity>
}