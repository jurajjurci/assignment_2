package sk.jurci.feature_movie.movie_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sk.jurci.feature_movie.ui.theme.Dimensions

@Preview
@Composable
internal fun LoadingItemPreview() {
    LoadingItem(
        modifier = Modifier.width(Dimensions.MovieCard.width),
    )
}

@Composable
internal fun LoadingItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(Dimensions.paddingSmall)
            .fillMaxWidth()
            .height(Dimensions.MovieCard.height),
        elevation = CardDefaults.cardElevation(Dimensions.elevation)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}