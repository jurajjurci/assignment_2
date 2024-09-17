package sk.jurci.core_network.repository

import sk.jurci.core_network.model.PopularMovieResponseDto

interface INetworkRepository {

    suspend fun getPopularMovieList(language: String, page: Int): PopularMovieResponseDto
}