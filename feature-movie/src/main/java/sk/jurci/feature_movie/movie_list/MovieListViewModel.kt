package sk.jurci.feature_movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import sk.jurci.core_repository.usecase.GetPopularMoviePagingFlowUseCase
import sk.jurci.feature_movie.model.mapper.toUiModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getPopularMoviePagingFlowUseCase: GetPopularMoviePagingFlowUseCase,
) : ViewModel() {

    val popularMovieList = getPopularMoviePagingFlowUseCase(
        language = Locale.getDefault().toLanguageTag(),
        cachedIn = viewModelScope,
    ).map { pagingData -> pagingData.map { it.toUiModel() } }
}