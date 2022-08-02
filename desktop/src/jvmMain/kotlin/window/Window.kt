package window

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.*
import data.AppIcons
import view.ActionIcon.ActionIcon
import view.ActionText.ActionText


@Composable
fun ApplicationScope.Window(
    icon: ActionIcon,
    menuBar: List<ActionText> = listOf(),
    content: @Composable ColumnScope.(scope: FrameWindowScope) -> Unit,
) {
    val onClose = ::exitApplication

    val state = rememberWindowState()
    val minimizingIcon = ActionIcon(
        leftClick = { state.isMinimized = !state.isMinimized },
        icon = painterResource(AppIcons.MINIMIZING_ICON),
        description = "-",
        color = Color.Green
    )
    val iconMax = remember { mutableStateOf(AppIcons.MAXIMIZING_ICON_FLOAT) }
    val maximizingIcon = ActionIcon(
        leftClick = {
            state.placement = if (state.placement == WindowPlacement.Floating) {
                iconMax.value = AppIcons.MAXIMIZING_ICON_FULLSCREEN
                WindowPlacement.Maximized
            } else {
                iconMax.value = AppIcons.MAXIMIZING_ICON_FLOAT
                WindowPlacement.Floating
            }
        },
        icon = painterResource(iconMax.value),
        description = "■",
        color = Color.Yellow
    )

    val closeIcon = ActionIcon(
        leftClick = {
            onClose()
        },
        icon = painterResource(AppIcons.CLOSE_ICON),
        description = "x",
        color = Color.Red
    )

    Window(
        icon = icon.icon,
        onCloseRequest = ::exitApplication,
        state = state,
        undecorated = true
    ) {
        val scope = this
        Column {
            AppWindowTitleBar(
                icon, minimizingIcon, maximizingIcon, closeIcon, menuBar
            )

            content(scope)
        }
    }
}
