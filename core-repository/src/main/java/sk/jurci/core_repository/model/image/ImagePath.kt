package sk.jurci.core_repository.model.image

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import sk.jurci.core_repository.util.Constants

@Serializable
abstract class ImagePath<T>(
    @Transient
    private val path: String = ""
) {

    abstract fun toUrl(size: T): String

    protected fun toUrl(size: String): String {
        return "${Constants.BASE_IMAGE_URL}/${size}${path}"
    }
}