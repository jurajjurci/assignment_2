package sk.jurci.feature_movie.ui.theme

import androidx.compose.ui.unit.dp

internal object Dimensions {
    val paddingSmall = 8.dp
    val paddingMedium = 16.dp
    val paddingLarge = 24.dp
    val paddingExtraLarge = 32.dp

    val elevation = 4.dp

    object MovieCard {
        private const val RATIO = 1.5f
        val width = 150.dp
        val height = width * RATIO
    }

    object FavouriteIcon {
        val size = 36.dp
    }
}