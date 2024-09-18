@file:OptIn(ExperimentalSharedTransitionApi::class)

package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.model.image.PosterSize
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.utils.formattedReleaseDate
import sk.jurci.feature_movie.utils.rating
import sk.jurci.feature_movie.ui.theme.Constants
import sk.jurci.feature_movie.ui.theme.Dimensions
import sk.jurci.feature_movie.ui.theme.posterTransactionKey

@Preview
@Composable
fun HeaderPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            Header(
                animatedVisibilityScope = this,
                movie = Movie.DEMO_MOVIE,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
internal fun SharedTransitionScope.Header(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Column(
        modifier = modifier.wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(Dimensions.paddingLarge)
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(Dimensions.MovieDetail.posterImageWidth)
                    .height(Dimensions.MovieDetail.posterImageHeight)
                    .sharedElement(
                        state = rememberSharedContentState(key = movie.posterTransactionKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = Constants.TRANSACTION_ANIMATION_DURATION)
                        }
                    ),
                model = movie.posterPath.toUrl(PosterSize.W_342),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(Dimensions.paddingLarge))
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (movie.adult) {
                    ForAdultsIndicator(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopEnd)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 65.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    TextRow(
                        label = stringResource(R.string.movie_detail_label_release_date),
                        value = movie.formattedReleaseDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                    TextRow(
                        label = stringResource(R.string.movie_detail_label_rating),
                        value = movie.rating,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}