package sk.jurci.core_repository.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.FavouriteMovie
import sk.jurci.core_database.model.MovieEntity

internal interface IDatabaseRepository {
    suspend fun insertAll(movieList: List<MovieEntity>, clear: Boolean = false)
    suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean)
    suspend fun isMovieMarkerAsFavourite(movieId: Long): Boolean
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>
    fun moviePagingSource(): PagingSource<Int, MovieEntity>
}