package sk.jurci.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieIdToGenreId")
data class MovieIdToGenreIdDto(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val movieId: Long,

    val genreId: Long,
)