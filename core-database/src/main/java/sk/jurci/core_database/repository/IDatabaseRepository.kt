package sk.jurci.core_database.repository

import androidx.paging.PagingSource
import sk.jurci.core_database.model.MovieEntity

interface IDatabaseRepository {

    suspend fun insertAll(movieList: List<MovieEntity>, clear: Boolean = false)
    fun moviePagingSource(): PagingSource<Int, MovieEntity>
}