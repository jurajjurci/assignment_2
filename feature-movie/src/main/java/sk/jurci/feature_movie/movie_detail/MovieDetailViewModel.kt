package sk.jurci.feature_movie.movie_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sk.jurci.core_repository.usecase.GetMovieUseCase
import sk.jurci.core_repository.usecase.MarkMovieAsFavouriteUseCase
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.model.mapper.toUiModel

@AssistedFactory
interface MovieDetailViewModelFactory {
    fun create(movieId: Long): MovieDetailViewModel
}

@HiltViewModel(assistedFactory = MovieDetailViewModelFactory::class)
class MovieDetailViewModel @AssistedInject constructor(
    @Assisted movieId: Long,
    private val markMovieAsFavouriteUseCase: MarkMovieAsFavouriteUseCase,
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    var movie by mutableStateOf<Movie?>(null)
        private set
    var isMovieFavourite by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val movie = getMovieUseCase(movieId)?.toUiModel()
                ?.also { isMovieFavourite = it.favourite }
            this@MovieDetailViewModel.movie = movie
        }
    }

    fun markMovieAsFavourite(movieId: Long, favourite: Boolean) {
        viewModelScope.launch {
            markMovieAsFavouriteUseCase(movieId, favourite)
            isMovieFavourite = favourite
        }
    }
}