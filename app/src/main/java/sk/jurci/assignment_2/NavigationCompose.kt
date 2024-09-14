package sk.jurci.assignment_2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import sk.jurci.assignment_2.utils.parcelableType
import sk.jurci.core.ui.Movie
import sk.jurci.feature_movie_detail.MovieDetailUi
import sk.jurci.feature_movie_list.MovieListUi
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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MovieList) {
        composable<Screen.MovieList> { MovieListUi() }

        composable<Screen.MovieDetail>(
            typeMap = mapOf(typeOf<Movie>() to parcelableType<Movie>())
        ) { backStackEntry ->
            val movie = backStackEntry.toRoute<Screen.MovieDetail>()
            MovieDetailUi(movie.movie)
        }

        composable<Screen.Settings> { SettingsUi() }

        composable<Screen.Info> { InfoUi() }
    }
}