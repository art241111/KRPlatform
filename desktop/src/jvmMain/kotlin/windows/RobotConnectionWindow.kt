package windows

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import robot.Robot
import robotsContext.RobotsContextImp
import view.actionIcon.ActionIcon
import windowView.Window

@Composable
fun ApplicationScope.RobotConnectionWindow(
    onConnect: (robot: Robot) -> Unit,
    coroutineScope: CoroutineScope,
    onClose: () -> Unit,
    icon: ActionIcon,
    robotsContextImp: RobotsContextImp
) {
    Window(
        onClose = onClose,
        icon = icon
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    val dataFlow: MutableStateFlow<String> = MutableStateFlow("")
                    robotsContextImp.connect(ip = "localhost", port = 9105, dataFlow, onConnect = { robot ->
                        onConnect(robot)
                        onClose()
                    })
                }
            }
        ) {
            Text("Connect")
        }
    }
}