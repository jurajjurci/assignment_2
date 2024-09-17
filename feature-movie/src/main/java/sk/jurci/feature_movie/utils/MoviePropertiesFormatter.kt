package sk.jurci.feature_movie.utils

import sk.jurci.core_repository.model.Movie
import java.text.SimpleDateFormat
import java.util.Locale


private fun formatDate(dateString: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("d. MMMM yyyy", Locale.getDefault())
    val date = inputFormatter.parse(dateString)
    return outputFormatter.format(date!!)
}

val Movie.rating: String
    get() {
        val formattedVoteAverage = String.format(Locale.getDefault(), "%.1f", voteAverage)
        return "$formattedVoteAverage ($voteCount)"
    }

val Movie.formattedReleaseDate: String
    get() = formatDate(releaseDate)

val Movie.formattedOriginalLanguage: String
    get() = Locale(originalLanguage).getDisplayLanguage(Locale.getDefault())
