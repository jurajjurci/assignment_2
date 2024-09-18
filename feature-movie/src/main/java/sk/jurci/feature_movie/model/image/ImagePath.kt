package sk.jurci.feature_movie.model.image

import kotlinx.serialization.Serializable
import sk.jurci.core_repository.util.Constants

@Serializable
abstract class ImagePath<T>(
    @Serializable(with = sk.jurci.feature_movie.utils.UrlEncodedStringSerializer::class)
    private val path: String = ""
) {

    abstract fun toUrl(size: T): String

    protected fun toUrl(size: String): String {
        return "${Constants.BASE_IMAGE_URL}/${size}${path}"
    }
}