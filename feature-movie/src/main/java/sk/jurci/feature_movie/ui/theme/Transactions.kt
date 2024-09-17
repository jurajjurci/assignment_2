package sk.jurci.feature_movie.ui.theme

import sk.jurci.core_repository.model.Movie

private const val POSTER = "poster"

val Movie.posterTransactionKey: String
    get() = "$POSTER/$id"