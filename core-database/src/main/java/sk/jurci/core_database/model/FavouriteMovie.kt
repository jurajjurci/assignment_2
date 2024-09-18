package sk.jurci.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class FavouriteMovie(

    @PrimaryKey
    val id: Long,
)