package sk.jurci.feature_movie.model

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize
import sk.jurci.feature_movie.model.image.BackdropPath
import sk.jurci.feature_movie.model.image.PosterPath
import sk.jurci.feature_movie.utils.UrlEncodedStringSerializer

@Serializable
@Parcelize
data class Movie(

    val id: Long,

    val adult: Boolean,

    val backdropPath: BackdropPath?,

    val originalLanguage: String,

    val originalTitle: String,

    @Serializable(with = UrlEncodedStringSerializer::class)
    val overview: String,

    val popularity: Double,

    val posterPath: PosterPath,

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
            posterPath = PosterPath(""),
            releaseDate = "1-12-2021",
            video = false,
            voteAverage = 0.5,
            voteCount = 100,
        )
    }
}