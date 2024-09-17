package sk.jurci.feature_movie.utils

object Constants {
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p"

    enum class PosterUrlSize {
        w92, w154, w185, w342, w500, w780, original,
    }

    enum class BackgroundUrlSize {
        w300, w780, w1280, original,
    }
}