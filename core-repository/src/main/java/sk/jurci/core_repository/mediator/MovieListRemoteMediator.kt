@file:OptIn(ExperimentalPagingApi::class)

package sk.jurci.core_repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import sk.jurci.core_database.AppDatabase
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_network.ApiService
import sk.jurci.core_repository.mapper.toEntity
import java.io.IOException
import javax.inject.Inject

internal class MovieListRemoteMediator @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, MovieEntity>() {

    private companion object {
        private const val LANGUAGE: String = "en-US"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val (currentPage, currentOrder) = when (loadType) {
                LoadType.REFRESH -> 1 to 0L
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1 to 0L
                    } else {
                        lastItem.page.inc() to lastItem.order.inc()
                    }
                }
            }

            val popularMovieResponse = withContext(ioDispatcher) {
                apiService.getPopularMovieList(
                    language = LANGUAGE,
                    page = currentPage,
                )
            }

            var order = currentOrder
            withContext(ioDispatcher) {
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        appDatabase.movieDao.clearAll()
                    }
                    appDatabase.movieDao.upsertAll(popularMovieResponse.results.map {
                        it.toEntity(currentPage, order++)
                    })
                }
            }

            return MediatorResult.Success(
                endOfPaginationReached = currentPage == popularMovieResponse.totalPages,
            )
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}