package window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import view.ActionIcon.ActionIcon
import view.ActionIcon.ActionIconView
import view.ActionText.ActionText
import view.ActionText.ActionTextView

@Composable
fun FrameWindowScope.AppWindowTitleBar(
    icon: ActionIcon,
    minimizingIcon: ActionIcon,
    maximizingIcon: ActionIcon,
    closeIcon: ActionIcon,
    menuBar: List<ActionText>
) = WindowDraggableArea {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ActionIconView(
            icon,
            height = 30.dp,
            width = 30.dp,
            contentColor = Color.Red
        )

        Box(modifier = Modifier.weight(1f)) {
            menuBar.forEach {
                ActionTextView(it)
            }
        }

        ControlButtons(minimizingIcon, maximizingIcon, closeIcon)
    }
}