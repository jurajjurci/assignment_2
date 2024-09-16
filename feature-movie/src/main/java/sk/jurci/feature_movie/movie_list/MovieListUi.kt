@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package sk.jurci.feature_movie.movie_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flow
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.model.Movie.Companion.DEMO_MOVIE
import sk.jurci.feature_movie.R

@Preview
@Composable
fun MovieListUiPreview() {
    val movieList = flow {
        emit(
            PagingData.from(
                listOf(
                    DEMO_MOVIE,
                    DEMO_MOVIE.copy(id = 1),
                    DEMO_MOVIE.copy(id = 2),
                    DEMO_MOVIE.copy(id = 3),
                    DEMO_MOVIE.copy(id = 4),
                    DEMO_MOVIE.copy(id = 5),
                    DEMO_MOVIE.copy(id = 6),
                    DEMO_MOVIE.copy(id = 7),
                )
            )
        )
    }.collectAsLazyPagingItems()
    MovieListUi(
        uiState = MovieListUiState(),
        movieList = movieList,
        refreshMovieList = {},
    )
}

@Composable
fun MovieListUi(
    uiState: MovieListUiState,
    movieList: LazyPagingItems<Movie>,
    refreshMovieList: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val minItemWidth = 300.dp
    val numberOfColumns = (screenWidth / minItemWidth).toInt().coerceAtLeast(1)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.movie_list_title))
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(numberOfColumns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(popularMovieList.itemCount) { index ->
                    GridItem(popularMovieList[index]!!)
                }
            }
        }
    }
}

@Composable
private fun GridItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = movie.title)
        }
    }
}