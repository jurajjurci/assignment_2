package sk.jurci.core_repository.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_repository.mapper.toDomainModel
import sk.jurci.core_repository.mediator.MovieListRemoteMediator
import sk.jurci.core_repository.model.MovieDomain

internal class MovieDomainRepository(
    private val pager: Pager<Int, MovieEntity>,
    private val movieListRemoteMediator: MovieListRemoteMediator,
    private val databaseRepository: IDatabaseRepository,
) : IMovieDomainRepository {

    override fun getPopularMovies(
        language: String,
        cachedIn: CoroutineScope
    ): Flow<PagingData<MovieDomain>> {
        movieListRemoteMediator.setLanguage(language)
        return pager.flow
            .distinctUntilChanged()
            .cachedIn(cachedIn)
            .combine(databaseRepository.getAllFavouriteMovies()) { moviePagingData, favouriteMovieList ->
                moviePagingData.map { movieEntity ->
                    val isFavourite = favouriteMovieList.any { it.id == movieEntity.id }
                    movieEntity.toDomainModel(favourite = isFavourite)
                }
            }
    }

    override suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean) {
        databaseRepository.markMovieAsFavourite(movieId = movieId, favourite = favourite)
    }

    private suspend fun isMovieMarkedAsFavourite(movieId: Long): Boolean {
        return databaseRepository.isMovieMarkerAsFavourite(movieId)
    }
}