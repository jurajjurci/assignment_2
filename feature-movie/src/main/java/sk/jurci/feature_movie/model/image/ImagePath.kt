package sk.jurci.feature_movie.model.image

import sk.jurci.core_repository.util.Constants

abstract class ImagePath<T>(
    private val path: String = ""
) {

    abstract fun toUrl(size: T): String

    protected fun toUrl(size: String): String {
        return "${Constants.BASE_IMAGE_URL}/${size}${path}"
    }
}