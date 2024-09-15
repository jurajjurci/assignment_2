package sk.jurci.core_repository.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import sk.jurci.core_network.ApiService
import sk.jurci.core_network.model.PopularMovieResponseEntity

class NetworkRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService,
) : INetworkRepository {

    override suspend fun getPopularMovies(
        language: String,
        page: Int,
    ): PopularMovieResponseEntity = withContext(ioDispatcher) {
        apiService.getPopularMovieList(language = language, page = page)
    }
}