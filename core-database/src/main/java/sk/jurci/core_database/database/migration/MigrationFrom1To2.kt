package sk.jurci.core_database.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal class MigrationFrom1To2 : Migration(startVersion = 2, endVersion = 3) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `favourite_movies` (`id` Long, PRIMARY KEY(`id`))")
    }
}