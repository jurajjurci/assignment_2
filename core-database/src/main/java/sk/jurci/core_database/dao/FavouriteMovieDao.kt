package sk.jurci.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.FavouriteMovie

@Dao
internal interface FavouriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteMovie: FavouriteMovie)

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>

    @Query("SELECT * FROM favourite_movies WHERE id like :movieId")
    suspend fun getFavouriteMovieEntity(movieId: Long): FavouriteMovie?

    @Delete
    suspend fun delete(favouriteMovie: FavouriteMovie)
}