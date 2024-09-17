package sk.jurci.core_repository.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.MovieEntity

interface IMovieEntityRepository {

    fun getPopularMovies(language: String): Flow<PagingData<MovieEntity>>

    suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean)
}