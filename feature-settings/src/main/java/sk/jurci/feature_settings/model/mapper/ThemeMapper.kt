package sk.jurci.feature_settings.model.mapper

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import sk.jurci.core_datastore.model.Theme

@Composable
fun Theme.mapToDarkThemeValue(): Boolean {
    return when (this) {
        Theme.Dark -> true
        Theme.Light -> false
        Theme.Auto -> isSystemInDarkTheme()
    }
}