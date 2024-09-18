package sk.jurci.core_datastore.preferences

import kotlinx.coroutines.flow.Flow
import sk.jurci.core_datastore.model.Theme

interface IPreferences {
    fun getSelectedTheme(): Flow<Theme>
    suspend fun saveSelectedTheme(theme: Theme)
}