package sk.jurci.core_datastore

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.jurci.core_datastore.preferences.IPreferences
import sk.jurci.core_datastore.preferences.Preferences
import sk.jurci.core_datastore.repository.DatastoreRepository
import sk.jurci.core_datastore.repository.IDatastoreRepository
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

    @Singleton
    @Provides
    internal fun providesPreferences(
        @ApplicationContext applicationContext: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): IPreferences = Preferences(
        context = applicationContext,
        ioDispatcher = ioDispatcher,
    )

    @Singleton
    @Provides
    fun providesDatastoreRepository(
        preferences: IPreferences,
    ): IDatastoreRepository = DatastoreRepository(preferences)
}