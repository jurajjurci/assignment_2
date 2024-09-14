package sk.jurci.core.network

import retrofit2.http.GET
import retrofit2.http.Query
import sk.jurci.core.network.model.MovieEntity

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): List<MovieEntity>
}