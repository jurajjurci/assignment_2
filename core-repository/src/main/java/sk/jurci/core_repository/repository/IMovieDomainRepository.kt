package sk.jurci.core_repository.repository

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_repository.model.MovieDomain

interface IMovieDomainRepository {
    fun getPopularMovies(language: String, cachedIn: CoroutineScope): Flow<PagingData<MovieDomain>>
    suspend fun getMovie(movieId: Long): MovieDomain?
    suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean)
}