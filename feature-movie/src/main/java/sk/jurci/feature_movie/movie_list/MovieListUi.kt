@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package sk.jurci.feature_movie.movie_list

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.model.Movie.Companion.DEMO_MOVIE
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.movie_list.ui.ErrorMessage
import sk.jurci.feature_movie.movie_list.ui.LoadingItem
import sk.jurci.feature_movie.movie_list.ui.MovieItem
import sk.jurci.feature_movie.ui.theme.Dimensions

@Preview
@Composable
internal fun MovieListUiPreview() {
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
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            MovieListUi(
                animatedVisibilityScope = this,
                onInfoButtonClick = {},
                onSettingsButtonClick = {},
                movieList = movieList,
                onMovieItemClick = {},
            )
        }
    }
}

@Composable
fun SharedTransitionScope.MovieListUi(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onInfoButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    movieList: LazyPagingItems<Movie>,
    onMovieItemClick: (Movie) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val minItemWidth = Dimensions.MovieCard.width
    val columnsCount = (screenWidth / minItemWidth).toInt().coerceAtLeast(1)

    val scrollBehavior = enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = movieList.loadState.refresh, key2 = movieList.loadState.append) {
        if (movieList.itemCount > 0 && movieList.loadState.isError) {
            scope.launch {
                snackBarHostState.showErrorSnackBar(context)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.movie_list_title)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = onInfoButtonClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.movie_list_button_info),
                            modifier = Modifier.size(Dimensions.iconSize)
                        )
                    }
                    IconButton(onClick = onSettingsButtonClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.movie_list_button_settings),
                            modifier = Modifier.size(Dimensions.iconSize)
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = movieList.loadState.refresh is LoadState.Loading,
            onRefresh = { movieList.refresh() },
        ) {
            if (movieList.loadState.refresh is LoadState.Error && movieList.itemCount == 0) {
                ErrorMessage(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimensions.paddingMedium),
                ) {
                    items(movieList.itemCount) { index ->
                        movieList[index]?.let { movie ->
                            MovieItem(
                                animatedVisibilityScope = animatedVisibilityScope,
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
        }
    }
}

private fun loadingItemsCount(moviesCount: Int, columnsCount: Int): Int {
    return columnsCount - moviesCount.mod(columnsCount)
}

private val CombinedLoadStates.isError: Boolean
    get() = this.append is LoadState.Error || this.refresh is LoadState.Error

private suspend fun SnackbarHostState.showErrorSnackBar(context: Context) {
    showSnackbar(
        message = context.getString(R.string.movie_list_error),
        actionLabel = context.getString(R.string.movie_list_snack_bar_close),
        duration = SnackbarDuration.Indefinite,
    )
}