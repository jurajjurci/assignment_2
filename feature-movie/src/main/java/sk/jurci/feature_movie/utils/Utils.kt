package sk.jurci.feature_movie.utils

fun convertPosterPathToImageUrl(
    imagePath: String,
    posterUrlSize: Constants.PosterUrlSize
): String {
    return "${Constants.BASE_IMAGE_URL}/${posterUrlSize.name}${imagePath}"
}

fun convertBackgroundPathToImageUrl(
    imagePath: String,
    backgroundUrlSize: Constants.BackgroundUrlSize,
): String {
    return "${Constants.BASE_IMAGE_URL}/${backgroundUrlSize.name}${imagePath}"
}