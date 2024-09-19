@file:OptIn(ExperimentalSharedTransitionApi::class)

package sk.jurci.assignment_2

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.serialization.Serializable
import sk.jurci.assignment_2.ui.theme.Assignment_2Theme
import sk.jurci.feature_movie.movie_detail.MovieDetailUi
import sk.jurci.feature_movie.movie_detail.MovieDetailViewModel
import sk.jurci.feature_movie.movie_detail.MovieDetailViewModelFactory
import sk.jurci.feature_movie.movie_list.MovieListUi
import sk.jurci.feature_movie.movie_list.MovieListViewModel
import sk.jurci.feature_settings.info.InfoUi
import sk.jurci.feature_settings.model.mapper.mapToDarkThemeValue
import sk.jurci.feature_settings.settings.SettingsUi
import sk.jurci.feature_settings.settings.SettingsViewModel

object Screen {
    @Serializable
    object MovieList

    @Serializable
    data class MovieDetail(val movieId: Long)

    @Serializable
    object Settings

    @Serializable
    object Info
}

@Composable
fun NavigationCompose() {
    SharedTransitionLayout {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.MovieList) {
            composable<Screen.MovieList> {
                val viewModel = hiltViewModel<MovieListViewModel>()
                val movieList = viewModel.popularMovieList.collectAsLazyPagingItems()
                MovieListUi(
                    animatedVisibilityScope = this,
                    onInfoButtonClick = { navController.navigate(Screen.Info) },
                    onSettingsButtonClick = { navController.navigate(Screen.Settings) },
                    movieList = movieList,
                    onMovieItemClick = { movie -> navController.navigate(Screen.MovieDetail(movie.id)) },
                )
            }

            composable<Screen.MovieDetail> { backStackEntry ->
                val movieRoute = backStackEntry.toRoute<Screen.MovieDetail>()
                val movieId = movieRoute.movieId
                val viewModel = hiltViewModel<MovieDetailViewModel, MovieDetailViewModelFactory> {
                    it.create(movieId)
                }
                val movie = viewModel.movie ?: return@composable
                MovieDetailUi(
                    animatedVisibilityScope = this,
                    movie = movie,
                    isMovieFavourite = viewModel.isMovieFavourite,
                    onBackPressed = navController::popBackStack,
                    onFavouriteButtonClick = { favourite ->
                        viewModel.markMovieAsFavourite(
                            movieId = movieId,
                            favourite = favourite,
                        )
                    }
                )
            }

            composable<Screen.Settings> {
                val viewModel = hiltViewModel<SettingsViewModel>()
                val selectedTheme = viewModel.currentTheme.collectAsStateWithLifecycle().value
                    ?: return@composable
                Assignment_2Theme(darkTheme = selectedTheme.mapToDarkThemeValue()) {
                    SettingsUi(
                        selectedTheme = selectedTheme,
                        onBackPressed = {
                            viewModel.saveSelectedTheme(selectedTheme)
                            navController.popBackStack()
                        },
                        setSelectedTheme = viewModel::setSelectedTheme,
                    )
                }
            }

            composable<Screen.Info> {
                InfoUi(
                    onBackPressed = navController::popBackStack
                )
            }
        }
    }
}