package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImagePluginView(
    modifier: Modifier = Modifier,
    pluginImage: ImageBitmap?,
    pluginName: String,
    fontSize: TextUnit = 48.sp,
    imagePadding: Dp = 0.dp,
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        val bit = pluginImage
        if (bit != null) {
            Image(bit, contentDescription = "", modifier = Modifier.size(100.dp).padding(imagePadding))
        } else {
            val letters =
                pluginName.split(" ").map { it.first() }.filterIndexed { index, _ -> index < 2 }
            Text(
                letters.joinToString("").uppercase(),
                fontSize = fontSize,
            )
        }
    }
}