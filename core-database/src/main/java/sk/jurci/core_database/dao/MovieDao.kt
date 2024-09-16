package sk.jurci.core_database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import sk.jurci.core_database.model.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movies ORDER BY page")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}