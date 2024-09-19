package sk.jurci.feature_movie.model.image

class PosterPath(path: String) : ImagePath<PosterSize>(path = path) {

    override fun toUrl(size: PosterSize): String {
        return toUrl(size.toString())
    }
}