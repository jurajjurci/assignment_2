package sk.jurci.core_datastore.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import sk.jurci.core_datastore.model.Theme


private const val PREFERENCES_NAME = "shared_preferences"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

internal class Preferences(
    context: Context,
    private val ioDispatcher: CoroutineDispatcher,
) : IPreferences {

    private companion object {
        private val SELECTED_THEME_KEY = stringPreferencesKey("selectedThemeKey")
    }

    private val dataStore = context.dataStore

    override fun getSelectedTheme(): Flow<Theme> {
        return dataStore.data.map { preferences ->
            withContext(ioDispatcher) {
                try {
                    val theme = preferences[SELECTED_THEME_KEY] ?: return@withContext Theme.Auto
                    Theme.valueOf(theme)
                } catch (e: Throwable) {
                    ensureActive()
                    Theme.Auto
                }
            }
        }
    }

    override suspend fun saveSelectedTheme(theme: Theme) {
        withContext(ioDispatcher) {
            dataStore.edit { preferences ->
                preferences[SELECTED_THEME_KEY] = theme.name
            }
        }
    }
}