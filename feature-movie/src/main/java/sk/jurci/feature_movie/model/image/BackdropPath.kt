package sk.jurci.feature_movie.model.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Parcelize
class BackdropPath(
    @Transient
    private val path: String = ""
) : ImagePath<BackdropSize>(path = path), Parcelable {

    override fun toUrl(size: BackdropSize): String {
        return toUrl(size.toString())
    }
}