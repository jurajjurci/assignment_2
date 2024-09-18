package sk.jurci.feature_settings.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import sk.jurci.core_datastore.model.Theme
import sk.jurci.feature_settings.model.mapper.mapToDarkThemeValue

@Composable
fun ChangeStatusBarColor(theme: Theme) {
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = theme.mapToDarkThemeValue()
    val backgroundColor = MaterialTheme.colorScheme.background
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = !isDarkTheme,
        )
    }
}