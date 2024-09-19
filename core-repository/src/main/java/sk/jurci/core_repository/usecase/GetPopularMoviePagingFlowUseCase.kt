package sk.jurci.core_repository.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_repository.model.MovieDomain
import sk.jurci.core_repository.repository.IMovieDomainRepository
import javax.inject.Inject

class GetPopularMoviePagingFlowUseCase @Inject constructor(
    private val movieDomainRepository: IMovieDomainRepository,
) {

    operator fun invoke(
        language: String,
        cachedIn: CoroutineScope,
    ): Flow<PagingData<MovieDomain>> = movieDomainRepository.getPopularMovies(language, cachedIn)
}
