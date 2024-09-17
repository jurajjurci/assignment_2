package sk.jurci.core_repository.usecase

import sk.jurci.core_repository.repository.IMovieEntityRepository
import javax.inject.Inject

class MarkMovieAsFavouriteUseCase @Inject constructor(
    private val movieEntityRepository: IMovieEntityRepository,
) {

    suspend operator fun invoke(
        movieId: Long,
        favourite: Boolean
    ) = movieEntityRepository.markMovieAsFavourite(movieId = movieId, favourite = favourite)
}