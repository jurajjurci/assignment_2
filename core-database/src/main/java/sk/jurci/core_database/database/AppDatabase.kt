package sk.jurci.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.jurci.core_database.dao.MovieDao
import sk.jurci.core_database.dao.MovieIdToGenreIdDao
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_database.model.MovieIdToGenreIdEntity

@Database(entities = [MovieEntity::class, MovieIdToGenreIdEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "Movie"
    }

    internal abstract val movieDao: MovieDao

    internal abstract val movieIdToGenreIdDao: MovieIdToGenreIdDao
}