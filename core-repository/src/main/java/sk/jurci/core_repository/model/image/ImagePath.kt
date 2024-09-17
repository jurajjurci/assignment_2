package sk.jurci.core_repository.model.image

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import sk.jurci.core_repository.util.Constants
import sk.jurci.core_repository.util.UrlEncodedStringSerializer

@Serializable
abstract class ImagePath<T>(
    @Serializable(with = UrlEncodedStringSerializer::class)
    private val path: String = ""
) {

    abstract fun toUrl(size: T): String

    protected fun toUrl(size: String): String {
        return "${Constants.BASE_IMAGE_URL}/${size}${path}"
    }
}