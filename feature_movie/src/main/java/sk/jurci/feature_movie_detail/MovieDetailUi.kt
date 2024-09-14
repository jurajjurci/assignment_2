package sk.jurci.feature_movie_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sk.jurci.core.ui.Movie

@Composable
fun MovieDetailUi(movie: Movie) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Movie Detail screen")
        }
    }
}