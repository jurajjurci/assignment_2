@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,
    ExperimentalSharedTransitionApi::class
)

package sk.jurci.feature_movie.movie_detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import sk.jurci.feature_movie.model.Movie
import sk.jurci.feature_movie.R
import sk.jurci.feature_movie.model.image.BackdropSize
import sk.jurci.feature_movie.model.image.PosterSize
import sk.jurci.feature_movie.movie_detail.ui.BackgroundImage
import sk.jurci.feature_movie.movie_detail.ui.Content
import sk.jurci.feature_movie.movie_detail.ui.Header
import sk.jurci.feature_movie.ui.theme.Dimensions

@Preview
@Composable
fun MovieDetailUiPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            MovieDetailUi(
                animatedVisibilityScope = this,
                movie = Movie.DEMO_MOVIE,
                isMovieFavourite = false,
                onBackPressed = {},
                onFavouriteButtonClick = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.MovieDetailUi(
    animatedVisibilityScope: AnimatedVisibilityScope,
    movie: Movie,
    isMovieFavourite: Boolean,
    onBackPressed: () -> Unit,
    onFavouriteButtonClick: (Boolean) -> Unit,
) {
    BackHandler { onBackPressed() }
    val scrollBehavior = pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .background(color = Color.Transparent)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.movie_detail_button_back),
                        )
                    }
                },
                actions = {
                    if (isMovieFavourite) {
                        IconButton(onClick = { onFavouriteButtonClick(false) }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.movie_detail_button_remove_from_favourite),
                                modifier = Modifier.size(Dimensions.iconSize)
                            )
                        }
                    } else {
                        IconButton(onClick = { onFavouriteButtonClick(true) }) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.movie_detail_button_add_to_favourite),
                                modifier = Modifier.size(Dimensions.iconSize)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Content(
                    modifier = Modifier
                        .wrapContentHeight()
                        .widthIn(max = Dimensions.MovieDetail.maximumWidth)
                        .align(Alignment.TopCenter)
                        .padding(top = Dimensions.MovieDetail.backdropImageHeight),
                    movie = movie,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    BackgroundImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimensions.MovieDetail.backdropImageHeight),
                        imageUrl = movie.backdropPath?.toUrl(BackdropSize.W_1280),
                        contentDescription = movie.title,
                        alternativeImage = movie.posterPath.toUrl(PosterSize.W_154)
                    )
                    Header(
                        animatedVisibilityScope = animatedVisibilityScope,
                        modifier = Modifier
                            .widthIn(max = Dimensions.MovieDetail.maximumWidth)
                            .align(Alignment.TopCenter),
                        movie = movie,
                    )
                }
            }
        }
    }
}