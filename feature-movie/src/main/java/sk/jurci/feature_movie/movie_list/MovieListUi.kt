@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package sk.jurci.feature_movie.movie_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.paging.LoadState
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
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.refreshing,
        onRefresh = refreshMovieList
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.movie_list_title)) }) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            if (movieList.loadState.refresh is LoadState.Error && movieList.itemCount == 0) {
                val scrollState = rememberScrollState()
                ErrorMessage(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    message = stringResource(R.string.movie_list_error_empty),
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(numberOfColumns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(movieList.itemCount) { index ->
                        movieList[index]?.let { movie -> MovieItem(movie) }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}