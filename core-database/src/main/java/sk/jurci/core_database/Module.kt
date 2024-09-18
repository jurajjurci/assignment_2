package sk.jurci.core_database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.jurci.core_database.dao.MovieDao
import sk.jurci.core_database.database.AppDatabase
import sk.jurci.core_database.database.migration.MigrationFrom1To2
import sk.jurci.core_database.repository.DatabaseRepository
import sk.jurci.core_database.repository.IDatabaseRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    @IoDispatcher
    internal fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    internal fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .addMigrations(MigrationFrom1To2())
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    internal fun providesShortcutDao(database: AppDatabase): MovieDao = database.movieDao

    @Provides
    @Singleton
    fun providesDatabaseRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        appDatabase: AppDatabase,
    ): IDatabaseRepository = DatabaseRepository(
        ioDispatcher = ioDispatcher,
        appDatabase = appDatabase,
    )
}