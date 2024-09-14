package sk.jurci.core.ui

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import sk.jurci.core.util.UrlEncodedStringSerializer

@Serializable
@Parcelize
data class Movie(

    @PrimaryKey
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

    val page: Long,

    var favourite: Boolean,
) : Parcelable