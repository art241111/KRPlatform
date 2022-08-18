package windowView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import data.AppIcons
import view.actionIcon.ActionIcon
import view.actionText.ActionText


@Composable
fun ApplicationScope.Window(
    icon: ActionIcon,
    menuBar: List<ActionText> = listOf(),
    onClose: () -> Unit = ::exitApplication,
    size: DpSize = DpSize(800.dp, 600.dp),
    alwaysOnTop: Boolean = false,
    background: Color = Color.White,
    contentColor: Color? = null,
    content: @Composable() (ColumnScope.(scope: FrameWindowScope) -> Unit),
) {
    val state = rememberWindowState(size = size)
    val minimizingIcon = ActionIcon(
        leftClick = { state.isMinimized = !state.isMinimized },
        icon = painterResource(AppIcons.MINIMIZING_ICON),
        description = "-",
        color = contentColor ?: Color.Black
    )
    val iconMax = remember { mutableStateOf(AppIcons.MAXIMIZING_ICON_FLOAT) }
    val maximizingIcon = ActionIcon(
        leftClick = {
            state.placement = if (state.placement == WindowPlacement.Floating) {
                iconMax.value = AppIcons.MAXIMIZING_ICON_FULLSCREEN
                state.position = WindowPosition(0.dp, 0.dp)
                WindowPlacement.Maximized
            } else {
                iconMax.value = AppIcons.MAXIMIZING_ICON_FLOAT
                state.position = WindowPosition(Alignment.Center)
                state.size = DpSize(400.dp, 400.dp)

                WindowPlacement.Floating
            }
        },
        icon = painterResource(iconMax.value),
        description = "■",
        color = contentColor ?: Color.Black
    )

    val closeIcon = ActionIcon(
        leftClick = {
            onClose()
        },
        icon = painterResource(AppIcons.CLOSE_ICON),
        description = "x",
        color = contentColor ?: Color.Black
    )

    Window(
        icon = icon.icon,
        onCloseRequest = onClose,
        state = state,
        undecorated = true,
        alwaysOnTop = alwaysOnTop
    ) {
        MaterialTheme(
            colors = lightColors(
                primary = Color(192, 0, 0)
            ),
            shapes = Shapes(small = RoundedCornerShape(50.dp), medium = RoundedCornerShape(10.dp))
        ) {
            val scope = this
            Card(
//                border = BorderStroke(0.5.dp, Color.LightGray),
                shape = RoundedCornerShape(0)
            ) {
                Column {
                    AppWindowTitleBar(
                        icon,
                        minimizingIcon,
                        maximizingIcon,
                        closeIcon,
                        menuBar,
                        background,
                        contentColor = contentColor
                    )

                    content(scope)
                }
            }
        }
    }
}
