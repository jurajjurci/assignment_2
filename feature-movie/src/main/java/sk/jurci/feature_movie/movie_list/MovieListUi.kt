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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import sk.jurci.core_repository.model.Movie
import sk.jurci.core_repository.model.Movie.Companion.DEMO_MOVIE
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.ui.theme.Dimensions

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
        movieList = movieList,
        onMovieItemClick = {},
    )
}

@Composable
fun MovieListUi(
    movieList: LazyPagingItems<Movie>,
    onMovieItemClick: (Movie) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val minItemWidth = Dimensions.MovieCard.width
    val columnsCount = (screenWidth / minItemWidth).toInt().coerceAtLeast(1)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = movieList.loadState.refresh is LoadState.Loading,
        onRefresh = { movieList.refresh() },
    )

    val scrollBehavior = enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = movieList.loadState.refresh, key2 = movieList.loadState.append) {
        if (movieList.itemCount > 0 && (movieList.loadState.append is LoadState.Error || movieList.loadState.refresh is LoadState.Error)) {
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.movie_list_error),
                    actionLabel = context.getString(R.string.movie_list_snack_bar_close),
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.movie_list_title)) },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
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
                    columns = GridCells.Fixed(columnsCount),
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = PaddingValues(Dimensions.paddingMedium),
                ) {
                    items(movieList.itemCount) { index ->
                        movieList[index]?.let { movie ->
                            MovieItem(
                                movie = movie,
                                onItemClick = onMovieItemClick,
                            )
                        }
                    }
                    if (movieList.loadState.append is LoadState.Loading) {
                        repeat(loadingItemsCount(movieList.itemCount, columnsCount)) {
                            item {
                                LoadingItem()
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = movieList.loadState.refresh is LoadState.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

private fun loadingItemsCount(moviesCount: Int, columnsCount: Int): Int {
    return columnsCount - moviesCount.mod(columnsCount)
}