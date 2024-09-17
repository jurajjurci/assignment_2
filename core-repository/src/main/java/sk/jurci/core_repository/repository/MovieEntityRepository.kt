package sk.jurci.core_repository.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_repository.mediator.MovieListRemoteMediator

internal class MovieEntityRepository(
    private val pager: Pager<Int, MovieEntity>,
    private val movieListRemoteMediator: MovieListRemoteMediator,
) : IMovieEntityRepository {

    override fun getPopularMovies(language: String): Flow<PagingData<MovieEntity>> {
        movieListRemoteMediator.setLanguage(language)
        return pager.flow
    }
}