package sk.jurci.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sk.jurci.core_database.model.MovieDto

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDto)

    @Delete
    suspend fun delete(movie: MovieDto)

    @Query("SELECT * FROM movies where page like :page")
    fun getMovies(page: Long): Flow<List<MovieDto>>
}