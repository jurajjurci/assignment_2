package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.usecase.FetchPopularMovieResponseEntityUseCase
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val fetchPopularMovieResponseUseCase: FetchPopularMovieResponseEntityUseCase,
) : ViewModel() {

    private val _page = MutableStateFlow(1)
    private var totalPages = 0

    val popularMovieList = _page
        .flatMapMerge { page ->
            println("PAGE: $page")
            fetchPopularMovieResponseUseCase(page = page)
        }
        .catch { e ->
            when {
                e is UnknownHostException -> {} // TODO handle no internet connection error

                else -> Timber.e(e)
            }
            // TODO handle error
        }
        .onEach { popularMovieResponse ->
            println("RESULT: total pages ${popularMovieResponse.totalPages}")
            popularMovieResponse.results.forEach {
                println("RESULT: title - ${it.title}")
            }
        }
        .map { popularMovieResponseEntityUseCase ->
            if (totalPages == 0) {
                totalPages = popularMovieResponseEntityUseCase.totalPages
            }
            popularMovieResponseEntityUseCase.results
        }
//            .runningFold(emptyList<Movie>()) { accumulator, value -> accumulator + value }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}