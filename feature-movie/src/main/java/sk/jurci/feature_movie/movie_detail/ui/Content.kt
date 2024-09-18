package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.utils.formattedOriginalLanguage
import sk.jurci.feature_movie.ui.theme.Dimensions

@Preview
@Composable
fun ContentPreview() {
    Content(
        modifier = Modifier.fillMaxWidth(),
        movie = Movie.DEMO_MOVIE,
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Spacer(modifier = Modifier.width(Dimensions.MovieDetail.posterImageWidth + Dimensions.paddingLarge))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = Dimensions.paddingMedium, end = Dimensions.paddingLarge)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.TopEnd),
                ) {
                    TextRow(
                        label = stringResource(R.string.movie_detail_label_original_title),
                        value = movie.originalTitle,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    TextRow(
                        label = stringResource(R.string.movie_detail_label_original_language),
                        value = movie.formattedOriginalLanguage,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = Dimensions.paddingLarge,
                    horizontal = 45.dp,
                )
        ) {
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify,
            )
        }
    }
}