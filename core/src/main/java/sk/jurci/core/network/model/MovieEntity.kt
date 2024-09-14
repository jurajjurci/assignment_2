package sk.jurci.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.jurci.core.util.UrlEncodedStringSerializer

@Serializable
data class MovieEntity(

    val id: Long,

    val adult: Boolean,

    @Serializable(with = UrlEncodedStringSerializer::class)
    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genre_ids")
    var genreIds: List<Long>,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,

    val popularity: Double,

    @Serializable(with = UrlEncodedStringSerializer::class)
    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("release_date")
    val releaseDate: String,

    val title: String,

    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,
)