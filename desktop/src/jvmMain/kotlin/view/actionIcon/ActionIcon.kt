package view.actionIcon

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class ActionIcon(
    val icon: Painter,
    val leftClick: () -> Unit = {},
    val rightClick: Map<@Composable () -> Unit, () -> Unit> = mapOf(),
    val description: String = "",
    val color: Color = Color.Transparent
)