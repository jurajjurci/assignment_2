package sk.jurci.core_repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import sk.jurci.core_repository.util.UrlEncodedStringSerializer

@Serializable
@Parcelize
data class Movie(

    val id: Long,

    val adult: Boolean,

    @Serializable(with = UrlEncodedStringSerializer::class)
    val backdropPath: String?,

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

    var favourite: Boolean,
) : Parcelable {

    companion object {
        val DEMO_MOVIE = Movie(
            id = 0,
            title = "Title",
            adult = false,
            backdropPath = null,
            favourite = false,
            originalLanguage = "en",
            originalTitle = "Original title",
            overview = "This is overview",
            popularity = 0.5,
            posterPath = "",
            releaseDate = "12/2021",
            video = false,
            voteAverage = 0.5,
            voteCount = 100,
        )
    }
}