package sk.jurci.feature_settings.utils

import android.content.Context

fun getScreenSize(context: Context): Pair<Int, Int> {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels to displayMetrics.heightPixels
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}