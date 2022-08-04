package window

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import view.ActionIcon.ActionIcon
import view.ActionIcon.ActionIconView


@Composable
fun ControlButtons(
    minimizingIcon: ActionIcon,
    maximizingIcon: ActionIcon,
    closeIcon: ActionIcon,
) {
    // Minimizing
    ActionIconView(minimizingIcon)

    // Maximizing
    ActionIconView(maximizingIcon)

    // Close
    ActionIconView(
        closeIcon,
        selectColor = Color.Red
    )
}