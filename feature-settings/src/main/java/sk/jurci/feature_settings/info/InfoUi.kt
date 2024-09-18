@file:OptIn(ExperimentalMaterial3Api::class)

package sk.jurci.feature_settings.info

import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import sk.jurci.feature_settings.BuildConfig
import sk.jurci.feature_settings.R
import sk.jurci.feature_settings.ui.theme.Dimensions
import sk.jurci.feature_settings.utils.capitalizeFirstLetter
import sk.jurci.feature_settings.utils.getScreenSize

@Preview
@Composable
fun InfoUiPreview() {
    InfoUi(
        onBackPressed = {}
    )
}

@Composable
fun InfoUi(
    onBackPressed: () -> Unit,
) {
    BackHandler { onBackPressed() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.info_title)) },
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
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .weight(Dimensions.infoLabelWeight),
                        text = stringResource(R.string.info_version_label),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(
                        modifier = Modifier.weight(Dimensions.infoValueWeight),
                        text = "${BuildConfig.VERSION_NUMBER} (${BuildConfig.VERSION_CODE})",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                }
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .weight(Dimensions.infoLabelWeight),
                        text = stringResource(R.string.info_developed_by_label),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(
                        modifier = Modifier.weight(Dimensions.infoValueWeight),
                        text = BuildConfig.CREATED_BY,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                }
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max)
                ) {
                    Text(
                        modifier = Modifier.weight(Dimensions.infoLabelWeight),
                        text = stringResource(R.string.info_device_info_label),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Column(
                        modifier = Modifier.weight(Dimensions.infoValueWeight),
                    ) {
                        Text(
                            modifier = Modifier.weight(Dimensions.infoValueWeight),
                            text = getInfo(LocalContext.current),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

private fun getInfo(context: Context): String {
    val (displayWidth, displayHeight) = getScreenSize(context)
    return StringBuilder()
        .append(context.getString(R.string.info_device_os_version))
        .append(" ")
        .append("${Build.VERSION.SDK_INT}${Build.SUPPORTED_ABIS[0]?.let { ", $it" }}")
        .append("\n")
        .append(context.getString(R.string.info_device_model))
        .append(" ")
        .append("${Build.MANUFACTURER.capitalizeFirstLetter()} (${Build.MODEL})")
        .append("\n")
        .append(context.getString(R.string.info_display))
        .append(" ")
        .append("$displayWidth x $displayHeight")
        .append("\n")
        .toString()
}