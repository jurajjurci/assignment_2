package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.usecase.GetPopularMoviePagingFlowUseCase
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMoviePagingFlowUseCase: GetPopularMoviePagingFlowUseCase,
) : ViewModel() {

    fun getPopularMoviePagingFlow(language: String): Flow<PagingData<Movie>> {
        return getPopularMoviePagingFlowUseCase(language)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
    }
}