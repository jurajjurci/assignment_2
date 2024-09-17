package sk.jurci.feature_movie.movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun ErrorMessagePreview() {
    ErrorMessage(
        modifier = Modifier.fillMaxSize(),
        message = "This is error message",
    )
}

@Composable
internal fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = message)
    }
}