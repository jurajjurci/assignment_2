package sk.jurci.core_database.repository

import androidx.paging.PagingSource
import sk.jurci.core_database.model.MovieEntity

interface IDatabaseRepository {

    suspend fun insertAll(movieList: List<MovieEntity>, clear: Boolean = false)
    suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean)
    fun moviePagingSource(): PagingSource<Int, MovieEntity>
}