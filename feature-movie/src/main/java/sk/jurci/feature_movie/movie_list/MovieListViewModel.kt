package sk.jurci.feature_movie.movie_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import sk.jurci.core_repository.usecase.FetchPopularMovieResponseEntityUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MovieListViewModel @Inject constructor(
    fetchPopularMovieResponseUseCase: FetchPopularMovieResponseEntityUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(MovieListUiState())
        private set

    val popularMovieList = fetchPopularMovieResponseUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    fun refresh() {
        viewModelScope.launch {
            uiState = uiState.copy(refreshing = true)
            delay(5.seconds)
            uiState = uiState.copy(refreshing = false)
        }
    }
}