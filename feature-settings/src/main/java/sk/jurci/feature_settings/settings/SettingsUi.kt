@file:OptIn(ExperimentalMaterial3Api::class)

package sk.jurci.feature_settings.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.max
import sk.jurci.core_datastore.model.Theme
import sk.jurci.feature_settings.R
import sk.jurci.feature_settings.ui.theme.Dimensions
import sk.jurci.feature_settings.ui.utils.ChangeStatusBarColor

@Preview
@Composable
internal fun SettingsUiPreview() {
    SettingsUi(
        selectedTheme = Theme.Dark,
        setSelectedTheme = {},
        onBackPressed = {},
    )
}

@Composable
fun SettingsUi(
    selectedTheme: Theme,
    onBackPressed: () -> Unit,
    setSelectedTheme: (Theme) -> Unit,
) {
    BackHandler { onBackPressed() }
    ChangeStatusBarColor(selectedTheme)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.settings_button_back),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = Dimensions.maxWidth)
                    .align(Alignment.TopCenter)
                    .padding(
                        horizontal = Dimensions.paddingExtraLarge,
                        vertical = Dimensions.paddingMedium,
                    )
            ) {
                Text(
                    text = stringResource(R.string.settings_theme_label),
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { setSelectedTheme(Theme.Auto) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme == Theme.Auto,
                        onClick = { setSelectedTheme(Theme.Auto) }
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(text = stringResource(R.string.settings_theme_value_auto))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { setSelectedTheme(Theme.Dark) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme == Theme.Dark,
                        onClick = { setSelectedTheme(Theme.Dark) }
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(text = stringResource(R.string.settings_theme_value_dark))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { setSelectedTheme(Theme.Light) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme == Theme.Light,
                        onClick = { setSelectedTheme(Theme.Light) }
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(text = stringResource(R.string.settings_theme_value_light))
                }
            }
        }
    }
}