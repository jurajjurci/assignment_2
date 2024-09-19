package sk.jurci.feature_movie.model.image

class BackdropPath(path: String) : ImagePath<BackdropSize>(path = path) {

    override fun toUrl(size: BackdropSize): String {
        return toUrl(size.toString())
    }
}