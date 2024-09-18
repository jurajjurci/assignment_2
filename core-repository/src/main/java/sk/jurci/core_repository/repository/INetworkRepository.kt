package sk.jurci.core_repository.repository

import sk.jurci.core_network.model.PopularMovieResponseDto

internal interface INetworkRepository {

    suspend fun getPopularMovieList(language: String, page: Int): PopularMovieResponseDto
}