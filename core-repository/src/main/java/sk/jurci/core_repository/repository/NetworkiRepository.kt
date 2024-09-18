package sk.jurci.core_repository.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import sk.jurci.core_network.model.PopularMovieResponseDto
import sk.jurci.core_network.service.ApiService

internal class NetworkRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService,
) : INetworkRepository {

    override suspend fun getPopularMovieList(language: String, page: Int): PopularMovieResponseDto {
        return withContext(ioDispatcher) {
            apiService.getPopularMovieList(language = language, page = page)
        }
    }
}