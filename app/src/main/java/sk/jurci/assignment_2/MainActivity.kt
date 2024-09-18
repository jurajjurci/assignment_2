package sk.jurci.assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import sk.jurci.assignment_2.ui.theme.Assignment_2Theme
import sk.jurci.feature_settings.model.mapper.mapToDarkThemeValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            val theme = viewModel.selectedTheme.collectAsStateWithLifecycle()
            Assignment_2Theme(darkTheme = theme.value.mapToDarkThemeValue()) {
                NavigationCompose()
            }
        }
    }
}