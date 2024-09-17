package sk.jurci.core_database.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import sk.jurci.core_database.database.AppDatabase
import sk.jurci.core_database.model.MovieEntity

internal class DatabaseRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase,
) : IDatabaseRepository {

    override suspend fun insertAll(movieList: List<MovieEntity>, clear: Boolean) {
        withContext(ioDispatcher) {
            appDatabase.withTransaction {
                if (clear) {
                    appDatabase.movieDao.clearAll()
                }
                appDatabase.movieDao.upsertAll(movieList)
            }
        }
    }

    override fun moviePagingSource(): PagingSource<Int, MovieEntity> {
        return appDatabase.movieDao.pagingSource()
    }
}