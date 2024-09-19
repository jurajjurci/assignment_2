package sk.jurci.core_repository.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import sk.jurci.core_database.database.AppDatabase
import sk.jurci.core_database.model.FavouriteMovie
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

    override suspend fun getMovie(movieId: Long): MovieEntity? {
        return withContext(ioDispatcher) {
            appDatabase.movieDao.getMovieEntity(movieId)
        }
    }

    override suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean) {
        withContext(ioDispatcher) {
            appDatabase.withTransaction {
                val favouriteMovie = FavouriteMovie(id = movieId)
                if (favourite) {
                    appDatabase.favouriteMovieDao.insert(favouriteMovie)
                } else {
                    appDatabase.favouriteMovieDao.delete(favouriteMovie)
                }
            }
        }
    }

    override suspend fun isMovieMarkerAsFavourite(movieId: Long): Boolean {
        return withContext(ioDispatcher) {
            appDatabase.favouriteMovieDao.getFavouriteMovieEntity(movieId) != null
        }
    }

    override fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>> {
        return appDatabase.favouriteMovieDao.getAllFavouriteMovies()
    }

    override fun moviePagingSource(): PagingSource<Int, MovieEntity> {
        return appDatabase.movieDao.pagingSource()
    }
}