package sk.jurci.core_repository.model.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Parcelize
class PosterPath(
    @Transient
    private val path: String = ""
) : ImagePath<PosterSize>(path = path), Parcelable {

    override fun toUrl(size: PosterSize): String {
        return toUrl(size.toString())
    }
}