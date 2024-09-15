package sk.jurci.core_repository.model

import sk.jurci.core_network.model.PopularMovieResponseEntity

data class PopularMovieResponse(
    val results: List<Movie>,
    val totalPages: Int,
)

fun PopularMovieResponseEntity.toUiModel(page: Int) = PopularMovieResponse(
    results = this.results.map { it.toUiModel(page) },
    totalPages = this.totalPages,
)
