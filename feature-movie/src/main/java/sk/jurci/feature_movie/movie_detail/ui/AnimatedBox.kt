package sk.jurci.feature_movie.movie_detail.ui

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.jurci.feature_movie.ui.theme.Constants

@Composable
internal fun AnimatedBox(
    modifier: Modifier = Modifier,
    posterUrl: String,
) {

    val colors = loadImageAndExtractColors(imageUrl = posterUrl)

    val infiniteTransition = rememberInfiniteTransition(label = "background")

    val targetOffset = with(LocalDensity.current) {
        1000.dp.toPx()
    }
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetOffset,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = Constants.HEADER_ANIMATION_DURATION,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "offset",
    )

    Box(
        modifier = modifier
            .blur(40.dp)
            .drawWithCache {
                val brushSize = 400f
                val brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(offset, offset),
                    end = Offset(offset + brushSize, offset + brushSize),
                    tileMode = TileMode.Mirror
                )
                onDrawBehind { drawRect(brush) }
            },
    )
}

@Composable
private fun loadImageAndExtractColors(imageUrl: String): List<Color> {
    val context = LocalContext.current
    val defaultColors = listOf(Color.Red, Color.Blue)
    var colors by remember { mutableStateOf(defaultColors) }
    LaunchedEffect(imageUrl) {
        colors = getDominantAndContrastingColors(context, imageUrl) ?: defaultColors
    }
    return colors
}

suspend fun getDominantAndContrastingColors(
    context: Context,
    imageUrl: String
): List<Color>? {
    return withContext(Dispatchers.IO) {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as? SuccessResult)?.drawable?.toBitmap()
            ?: return@withContext null

        val palette = Palette.from(result).generate()
        val dominantColor = palette.getDominantColor(0)
        val vibrantColor = palette.getVibrantColor(0)
        return@withContext listOf(Color(dominantColor), Color(vibrantColor))
    }
}