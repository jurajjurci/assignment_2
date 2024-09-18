package sk.jurci.feature_movie.model.mapper

import sk.jurci.core_repository.model.MovieDomain
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.model.image.BackdropPath
import sk.jurci.feature_movie.model.image.PosterPath

internal fun MovieDomain.toUiModel() = Movie(
    id = this.id,
    adult = this.adult,
    backdropPath = this.backdropPath?.let { BackdropPath(it) },
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = PosterPath(this.posterPath),
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    favourite = favourite,
)