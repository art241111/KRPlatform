package ui.windowView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import ui.views.actionIcon.ActionIcon
import ui.views.actionIcon.ActionIconView
import ui.views.actionText.ActionText
import ui.views.actionText.ActionTextView

@Composable
fun FrameWindowScope.AppWindowTitleBar(
    icon: ActionIcon,
    minimizingIcon: ActionIcon,
    maximizingIcon: ActionIcon,
    closeIcon: ActionIcon,
    menuBar: List<ActionText>,
    background: Color = Color.White,
    contentColor: Color? = null
) = WindowDraggableArea {
    Row(
        Modifier.fillMaxWidth()
            .height(30.dp).background(color = background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIconView(
            icon,
            height = 40.dp,
            width = 40.dp,
            contentColor = contentColor ?: Color.Red,
            selectColor = Color.Transparent,
            iconPadding = 6.dp
        )

        Box(modifier = Modifier.weight(1f)) {
            menuBar.forEach {
                ActionTextView(it)
            }
        }

        ControlButtons(minimizingIcon, maximizingIcon, closeIcon)
    }
}