package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = Dimensions.paddingLarge, top = Dimensions.paddingMedium)
                .align(alignment = Alignment.End),
            horizontalAlignment = Alignment.Start,
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = Dimensions.paddingLarge, horizontal = Dimensions.paddingExtraLarge)
        ) {
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify,
            )
        }
    }
}