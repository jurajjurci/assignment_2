@file:OptIn(ExperimentalPagingApi::class)

package sk.jurci.core_repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import retrofit2.HttpException
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_database.repository.IDatabaseRepository
import sk.jurci.core_network.repository.INetworkRepository
import sk.jurci.core_repository.mapper.toEntity
import java.io.IOException

internal class MovieListRemoteMediator(
    private val databaseRepository: IDatabaseRepository,
    private val networkRepository: INetworkRepository,
) : RemoteMediator<Int, MovieEntity>() {

    private companion object {
        private const val LANGUAGE: String = "en-US"
    }

    private var language: String = LANGUAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPaging: Paging = when (loadType) {
                LoadType.REFRESH -> Paging()
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        Paging()
                    } else {
                        Paging(lastItem)
                    }
                }
            }

            val popularMovieResponse = networkRepository.getPopularMovieList(
                language = language,
                page = currentPaging.page,
            )

            databaseRepository.insertAll(
                movieList = popularMovieResponse.results.map {
                    it.toEntity(
                        page = currentPaging.page,
                        order = currentPaging.itemOrder,
                    )
                },
                clear = loadType == LoadType.REFRESH,
            )

            return MediatorResult.Success(
                endOfPaginationReached = currentPaging.page == popularMovieResponse.totalPages,
            )
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    fun setLanguage(language: String) {
        this.language = language
    }
}