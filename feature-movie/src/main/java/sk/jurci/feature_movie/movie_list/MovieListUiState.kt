package sk.jurci.feature_movie.movie_list

data class MovieListUiState(
    val error: Throwable? = null,
    val refreshing: Boolean = false,
    val loadingNextPage: Boolean = false,
)