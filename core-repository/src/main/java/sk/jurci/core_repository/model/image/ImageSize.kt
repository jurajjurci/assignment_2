package sk.jurci.core_repository.model.image

enum class BackdropSize {
    W_300(), W_780, W_1280, ORIGINAL;

    override fun toString(): String {
        return name.lowercase().replace("_", "")
    }
}

enum class PosterSize {
    W_92, W_154, W_185, W_342, W_500, W_780, ORIGINAL;

    override fun toString(): String {
        return name.lowercase().replace("_", "")
    }
}