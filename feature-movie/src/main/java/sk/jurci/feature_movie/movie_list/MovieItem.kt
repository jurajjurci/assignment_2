@file:OptIn(ExperimentalSharedTransitionApi::class)

package sk.jurci.feature_movie.movie_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.model.Movie.Companion.DEMO_MOVIE
import sk.jurci.core_repository.model.image.PosterSize
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.ui.theme.Constants
import sk.jurci.feature_movie.ui.theme.Dimensions
import sk.jurci.feature_movie.ui.theme.posterTransactionKey

@Preview
@Composable
internal fun MovieItemPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            MovieItem(
                modifier = Modifier.width(Dimensions.MovieCard.width),
                animatedVisibilityScope = this,
                movie = DEMO_MOVIE,
                onItemClick = {},
            )
        }
    }
}

@Composable
internal fun SharedTransitionScope.MovieItem(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    movie: Movie,
    onItemClick: (Movie) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(Dimensions.paddingSmall)
            .fillMaxWidth()
            .height(Dimensions.MovieCard.height),
        elevation = CardDefaults.cardElevation(Dimensions.elevation)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = ripple(color = Color.Black),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onItemClick(movie)
            }) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .sharedElement(
                        state = rememberSharedContentState(key = movie.posterTransactionKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = Constants.TRANSACTION_ANIMATION_DURATION)
                        }
                    ),
                model = movie.posterPath.toUrl(PosterSize.W_154),
                contentScale = ContentScale.Crop,
                contentDescription = movie.title,
            )

            val icon: ImageVector
            val description: String
            if (movie.favourite) {
                icon = Icons.Default.Favorite
                description = stringResource(R.string.movie_list_favourite_icon_filled)
            } else {
                icon = Icons.Default.FavoriteBorder
                description = stringResource(R.string.movie_list_favourite_icon_empty)
            }
            FavouriteIcon(
                modifier = Modifier
                    .padding(Dimensions.paddingSmall)
                    .align(Alignment.TopEnd),
                icon = icon,
                description = description,
            )
            Title(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                text = movie.title,
            )
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    text: String,
) {
    val tileSize = with(LocalDensity.current) {
        Dimensions.paddingMedium.toPx()
    }
    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(
                brush = Brush.verticalGradient(
                    endY = tileSize,
                    tileMode = TileMode.Clamp,
                    colorStops = arrayOf(
                        0.0f to Color.Transparent,
                        0.5f to Color.Black.copy(alpha = 0.6f),
                        1f to Color.Black.copy(alpha = 0.8f),
                    ),
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = Dimensions.paddingMedium,
                vertical = Dimensions.paddingMedium,
            ),
            maxLines = 1,
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun FavouriteIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    description: String,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(CircleShape)
            .background(Color.DarkGray.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(Dimensions.FavouriteIcon.size)
                .padding(6.dp),
            imageVector = icon,
            contentDescription = description,
            tint = Color.White.copy(alpha = 0.8f),
        )
    }
}