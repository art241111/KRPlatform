package windowView

import androidx.compose.foundation.ExperimentalFoundationApi
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
import view.actionIcon.ActionIcon
import view.actionIcon.ActionIconView
import view.actionText.ActionText
import view.actionText.ActionTextView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FrameWindowScope.AppWindowTitleBar(
    icon: ActionIcon,
    minimizingIcon: ActionIcon,
    maximizingIcon: ActionIcon,
    closeIcon: ActionIcon,
    menuBar: List<ActionText>
) = WindowDraggableArea {
    Row(
        Modifier.fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIconView(
            icon,
            height = 40.dp,
            width = 40.dp,
            contentColor = Color.Red,
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