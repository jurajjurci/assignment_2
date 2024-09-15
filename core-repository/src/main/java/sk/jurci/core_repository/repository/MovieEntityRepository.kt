package sk.jurci.core_repository.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.MovieEntity

class MovieEntityRepository(
    private val pager: Pager<Int, MovieEntity>,
) : IMovieEntityRepository {

    override fun getPopularMovies(): Flow<PagingData<MovieEntity>> = pager.flow
}