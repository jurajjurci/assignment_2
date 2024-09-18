package sk.jurci.core_datastore.repository

import kotlinx.coroutines.flow.Flow
import sk.jurci.core_datastore.model.Theme
import sk.jurci.core_datastore.preferences.IPreferences

internal class DatastoreRepository(
    private val preferences: IPreferences,
) : IDatastoreRepository {

    override fun getSelectedTheme(): Flow<Theme> = preferences.getSelectedTheme()

    override suspend fun saveSelectedTheme(theme: Theme) = preferences.saveSelectedTheme(theme)
}