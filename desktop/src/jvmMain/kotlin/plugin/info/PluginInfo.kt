package plugin.info

import androidx.compose.ui.graphics.ImageBitmap

data class PluginInfo(
    val fileName: String = "",
    val mainClassDir: String = "",
    val pluginName: String = "",
) {
    var pluginImage: ImageBitmap? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }
}