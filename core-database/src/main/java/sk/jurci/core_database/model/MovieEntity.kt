package sk.jurci.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey
    val id: Long,

    val adult: Boolean,

    val backdropPath: String?,

    val originalLanguage: String,

    val originalTitle: String,

    val overview: String,

    val popularity: Double,

    val posterPath: String,

    val releaseDate: String,

    val title: String,

    val video: Boolean,

    val voteAverage: Double,

    val voteCount: Int,

    val page: Int = 0,

    var favourite: Boolean = false,
)