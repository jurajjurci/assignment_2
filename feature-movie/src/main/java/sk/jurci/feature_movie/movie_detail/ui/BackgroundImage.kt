package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
internal fun BackgroundImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String,
    alternativeImage: String,
) {
    if (imageUrl != null) {
        Box(
            modifier = modifier
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Transparent,
                                0.5f to Color.Black.copy(alpha = 0.6f),
                                1f to Color.Black.copy(alpha = 0.8f),
                            ),
                        )
                    ),
            )
        }
    } else {
        AnimatedBox(
            modifier = modifier,
            posterUrl = alternativeImage,
        )
    }
}