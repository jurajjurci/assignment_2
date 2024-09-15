package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import sk.jurci.core_repository.usecase.FetchPopularMovieResponseEntityUseCase
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    fetchPopularMovieResponseUseCase: FetchPopularMovieResponseEntityUseCase,
) : ViewModel() {


    val popularMovieList = fetchPopularMovieResponseUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5.seconds.inWholeMilliseconds),
            initialValue = PagingData.empty()
        )
}