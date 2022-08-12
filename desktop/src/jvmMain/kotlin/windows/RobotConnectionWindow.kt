package windows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
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
    val dataFlow: MutableStateFlow<String> = MutableStateFlow("")
    val state = dataFlow.collectAsState()

    Window(
        onClose = onClose,
        icon = icon,
        size = DpSize(400.dp, 600.dp),
        alwaysOnTop = true
    ) {
        Column {
            Button(
                onClick = {
                    coroutineScope.launch {
                        robotsContextImp.connect(ip = "localhost", port = 9105, dataFlow, onConnect = { robot ->
                            onConnect(robot)
                            onClose()
                        })
                    }
                }
            ) {
                Text("Connect")
            }

            Spacer(Modifier.weight(1f))
            Text(state.value)
        }
    }
}