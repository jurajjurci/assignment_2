package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import sk.jurci.feature_movie.R

@Composable
fun ForAdultsIndicator(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.movie_detail_label_for_adults),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}