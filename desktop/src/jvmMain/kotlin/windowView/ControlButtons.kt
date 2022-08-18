package windowView

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import view.actionIcon.ActionIcon
import view.actionIcon.ActionIconView


@Composable
fun ControlButtons(
    minimizingIcon: ActionIcon,
    maximizingIcon: ActionIcon,
    closeIcon: ActionIcon,
) {
    // Minimizing
    ActionIconView(
        minimizingIcon,
        contentColor = minimizingIcon.color
    )

    // Maximizing
    ActionIconView(
        maximizingIcon,
        contentColor = maximizingIcon.color
    )

    // Close
    ActionIconView(
        closeIcon,
        contentColor = closeIcon.color,
        selectColor = Color.Red
    )
}