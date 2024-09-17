package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import sk.jurci.core_repository.usecase.FetchPopularMovieResponseEntityUseCase
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    fetchPopularMovieResponseUseCase: FetchPopularMovieResponseEntityUseCase,
) : ViewModel() {

    val popularMovieList = fetchPopularMovieResponseUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
}