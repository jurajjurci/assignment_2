package sk.jurci.core_repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import sk.jurci.core_network.model.MovieEntity
import sk.jurci.core_repository.util.UrlEncodedStringSerializer

@Serializable
@Parcelize
data class Movie(

    val id: Long,

    val adult: Boolean,

    @Serializable(with = UrlEncodedStringSerializer::class)
    val backdropPath: String,

    val originalLanguage: String,

    val originalTitle: String,

    val overview: String,

    val popularity: Double,

    @Serializable(with = UrlEncodedStringSerializer::class)
    val posterPath: String,

    val releaseDate: String,

    val title: String,

    val video: Boolean,

    val voteAverage: Double,

    val voteCount: Int,

    val page: Int,

    var favourite: Boolean,
) : Parcelable

fun MovieEntity.toUiModel(page: Int) = Movie(
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
    favourite = false, // TODO zmenit
)