package sk.jurci.feature_movie.movie_detail.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
internal fun TextRow(
    label: String,
    value: String,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = label,
            style = style,
            color = color,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
        TextSpacer(
            style = style,
            color = color,
        )
        Text(
            text = value,
            style = style,
            color = color,
            maxLines = 1,
        )
    }
}

@Composable
private fun TextSpacer(
    style: TextStyle,
    color: Color,
) {
    Text(
        text = ": ",
        fontWeight = FontWeight.Bold,
        style = style,
        color = color,
    )
}