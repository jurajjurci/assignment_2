package sk.jurci.core_repository.usecase

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sk.jurci.core_repository.mapper.toUiModel
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.repository.IMovieEntityRepository
import javax.inject.Inject

class FetchPopularMovieResponseEntityUseCase @Inject constructor(
    private val movieEntityRepository: IMovieEntityRepository,
) {

    operator fun invoke(): Flow<PagingData<Movie>> = movieEntityRepository.getPopularMovies()
        .map { pagingData -> pagingData.map { it.toUiModel() } }
}
