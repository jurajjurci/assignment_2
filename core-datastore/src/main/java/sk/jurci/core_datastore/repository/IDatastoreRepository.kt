package sk.jurci.core_datastore.repository

import kotlinx.coroutines.flow.Flow
import sk.jurci.core_datastore.model.Theme

interface IDatastoreRepository {
    fun getSelectedTheme(): Flow<Theme>
    suspend fun saveSelectedTheme(theme: Theme)
}