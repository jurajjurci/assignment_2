@file:OptIn(ExperimentalSharedTransitionApi::class)

package sk.jurci.assignment_2

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.serialization.Serializable
import sk.jurci.assignment_2.utils.parcelableType
import sk.jurci.core_repository.model.Movie
import sk.jurci.feature_movie.movie_detail.MovieDetailUi
import sk.jurci.feature_movie.movie_detail.MovieDetailViewModel
import sk.jurci.feature_movie.movie_detail.MovieDetailViewModelFactory
import sk.jurci.feature_movie.movie_list.MovieListUi
import sk.jurci.feature_movie.movie_list.MovieListViewModel
import sk.jurci.feature_settings.info.InfoUi
import sk.jurci.feature_settings.settings.SettingsUi
import kotlin.reflect.typeOf

object Screen {
    @Serializable
    object MovieList

    @Serializable
    data class MovieDetail(val movie: Movie)

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
                    movieList = movieList,
                    onMovieItemClick = { movie -> navController.navigate(Screen.MovieDetail(movie)) }
                )
            }

            composable<Screen.MovieDetail>(
                typeMap = mapOf(typeOf<Movie>() to parcelableType<Movie>())
            ) { backStackEntry ->
                val movieRoute = backStackEntry.toRoute<Screen.MovieDetail>()
                val movie = movieRoute.movie
                val viewModel = hiltViewModel<MovieDetailViewModel, MovieDetailViewModelFactory> {
                    it.create(movie.favourite)
                }
                MovieDetailUi(
                    animatedVisibilityScope = this,
                    movie = movie,
                    isMovieFavourite = viewModel.isMovieFavourite,
                    onBackPressed = navController::popBackStack,
                    onFavouriteButtonClick = { favourite ->
                        viewModel.markMovieAsFavourite(
                            movieId = movie.id,
                            favourite = favourite,
                        )
                    }
                )
            }

            composable<Screen.Settings> { SettingsUi() }

            composable<Screen.Info> { InfoUi() }
        }
    }
}