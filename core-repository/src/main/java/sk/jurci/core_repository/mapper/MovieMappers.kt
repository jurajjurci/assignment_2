package sk.jurci.core_repository.mapper

import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_network.model.MovieDto
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.model.image.BackdropPath
import sk.jurci.core_repository.model.image.PosterPath

internal fun MovieEntity.toUiModel() = Movie(
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
    favourite = this.favourite,
)

internal fun MovieDto.toEntity(page: Int, order: Long) = MovieEntity(
    id = this.id,
    adult = this.adult,
    backdropPath = this.backdropPath,
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    page = page,
    order = order,
    favourite = false,
)