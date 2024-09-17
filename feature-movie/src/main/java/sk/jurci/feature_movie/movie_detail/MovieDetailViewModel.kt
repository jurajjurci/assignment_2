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
import kotlinx.coroutines.launch
import sk.jurci.core_repository.usecase.MarkMovieAsFavouriteUseCase

@AssistedFactory
interface MovieDetailViewModelFactory {
    fun create(isMovieFavourite: Boolean): MovieDetailViewModel
}

@HiltViewModel(assistedFactory = MovieDetailViewModelFactory::class)
class MovieDetailViewModel @AssistedInject constructor(
    private val markMovieAsFavouriteUseCase: MarkMovieAsFavouriteUseCase,
    @Assisted isMovieFavourite: Boolean,
) : ViewModel() {

    var isMovieFavourite by mutableStateOf(isMovieFavourite)

    fun markMovieAsFavourite(movieId: Long, favourite: Boolean) {
        viewModelScope.launch {
            markMovieAsFavouriteUseCase(movieId, favourite)
            isMovieFavourite = favourite
        }
    }
}