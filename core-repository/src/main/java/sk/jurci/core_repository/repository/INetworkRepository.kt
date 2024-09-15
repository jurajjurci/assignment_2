package sk.jurci.core_repository.repository

import sk.jurci.core_network.model.PopularMovieResponseEntity

interface INetworkRepository {

    suspend fun getPopularMovies(language: String, page: Int): PopularMovieResponseEntity
}