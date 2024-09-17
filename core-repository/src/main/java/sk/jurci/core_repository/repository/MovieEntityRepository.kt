package sk.jurci.core_repository.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_database.repository.IDatabaseRepository
import sk.jurci.core_repository.mediator.MovieListRemoteMediator

internal class MovieEntityRepository(
    private val pager: Pager<Int, MovieEntity>,
    private val movieListRemoteMediator: MovieListRemoteMediator,
    private val databaseRepository: IDatabaseRepository,
) : IMovieEntityRepository {

    override fun getPopularMovies(language: String): Flow<PagingData<MovieEntity>> {
        movieListRemoteMediator.setLanguage(language)
        return pager.flow
    }

    override suspend fun markMovieAsFavourite(movieId: Long, favourite: Boolean) {
        databaseRepository.markMovieAsFavourite(movieId = movieId, favourite = favourite)
    }
}