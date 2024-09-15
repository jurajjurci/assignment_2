package sk.jurci.core_repository.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import sk.jurci.core_repository.model.PopularMovieResponse
import sk.jurci.core_repository.model.toUiModel
import sk.jurci.core_repository.repository.INetworkRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.pow
import kotlin.time.Duration.Companion.seconds

class FetchPopularMovieResponseEntityUseCase @Inject constructor(
    private val networkRepository: INetworkRepository,
) {

    companion object {
        private const val RETRY_ATTEMPT_COUNT = 2
    }

    operator fun invoke(
        language: String = "en-US",
        page: Int,
    ): Flow<PopularMovieResponse> = flow {
        emit(networkRepository.getPopularMovies(language = language, page = page).toUiModel(page))
    }.retryWhen { cause, attempt ->
        delay(1.seconds.times(2f.pow(attempt.toInt()).toInt()))
        attempt < RETRY_ATTEMPT_COUNT && cause !is CancellationException
    }
}
