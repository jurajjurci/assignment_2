package sk.jurci.feature_movie.movie_list

data class MovieListUiState(
    val error: Throwable,
    val loading: Boolean,
)