package sk.jurci.core_repository.usecase

import sk.jurci.core_repository.repository.IMovieDomainRepository
import javax.inject.Inject

class MarkMovieAsFavouriteUseCase @Inject constructor(
    private val movieDomainRepository: IMovieDomainRepository,
) {

    suspend operator fun invoke(
        movieId: Long,
        favourite: Boolean
    ) = movieDomainRepository.markMovieAsFavourite(movieId = movieId, favourite = favourite)
}