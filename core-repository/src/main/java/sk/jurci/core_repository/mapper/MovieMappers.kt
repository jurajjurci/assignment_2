package sk.jurci.core_repository.mapper

import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_network.model.MovieDto
import sk.jurci.core_repository.model.Movie

fun MovieEntity.toUiModel() = Movie(
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
    favourite = this.favourite,
)

fun MovieDto.toEntity(page: Int) = MovieEntity(
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
    favourite = false,
)