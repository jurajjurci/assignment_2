package sk.jurci.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.jurci.core_database.dao.MovieDao
import sk.jurci.core_database.dao.MovieIdToGenreIdDao
import sk.jurci.core_database.model.MovieDto
import sk.jurci.core_database.model.MovieIdToGenreIdDto

@Database(entities = [MovieDto::class, MovieIdToGenreIdDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "Movie"
    }

    abstract fun movieDao(): MovieDao

    abstract fun movieIdToGenreIdDao(): MovieIdToGenreIdDao
}