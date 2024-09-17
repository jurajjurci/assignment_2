@file:OptIn(ExperimentalSharedTransitionApi::class)

package sk.jurci.feature_movie.movie_detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.model.image.BackdropPath
import sk.jurci.core_repository.model.image.BackdropSize
import sk.jurci.core_repository.model.image.PosterSize
import sk.jurci.feature_movie.ui.theme.Constants
import sk.jurci.feature_movie.ui.theme.Dimensions
import sk.jurci.feature_movie.ui.theme.posterTransactionKey

@Composable
internal fun SharedTransitionScope.Header(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Box(
        modifier = Modifier.wrapContentSize(),
    ) {
        // TODO remove
        if (movie.id == 533535L) {
            val backdropPath = movie.backdropPath ?: BackdropPath()
//        movie.backdropPath?.let { backdropPath ->
            Box(modifier = modifier) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = backdropPath.toUrl(BackdropSize.W_1280),
                    contentDescription = movie.title,
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
                posterUrl = movie.posterPath.toUrl(PosterSize.W_154),
            )
        }

        AsyncImage(
            modifier = Modifier
                .width(Dimensions.MovieDetail.posterImageWidth)
                .height(Dimensions.MovieDetail.posterImageHeight)
                .padding(start = Dimensions.paddingLarge, top = Dimensions.paddingLarge)
                .sharedElement(
                    state = rememberSharedContentState(key = movie.posterTransactionKey),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = Constants.TRANSACTION_ANIMATION_DURATION)
                    }
                ),
            model = movie.posterPath.toUrl(PosterSize.W_154),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
        )
    }
}