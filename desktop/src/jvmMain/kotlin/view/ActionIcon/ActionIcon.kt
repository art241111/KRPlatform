package view.ActionIcon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class ActionIcon(
    val icon: Painter,
    val leftClick: () -> Unit = {},
    val rightClick: Map<String, () -> Unit> = mapOf(),
    val description: String = "",
    val color: Color = Color.Transparent
)