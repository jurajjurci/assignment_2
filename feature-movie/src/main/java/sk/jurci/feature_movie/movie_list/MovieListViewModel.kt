package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import sk.jurci.core_repository.usecase.GetPopularMoviePagingFlowUseCase
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getPopularMoviePagingFlowUseCase: GetPopularMoviePagingFlowUseCase,
) : ViewModel() {

    val popularMovieList = getPopularMoviePagingFlowUseCase(Locale.getDefault().toLanguageTag())
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
}