package ui.windows.robotConnection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import plugin.conetexts.robotsContext.RobotsContextImp
import robot.Robot
import ui.robotConnection.ConnectionWindow
import ui.views.actionIcon.ActionIcon
import utils.ConnectionSpecificationList

@Composable
fun ApplicationScope.RobotConnectionWindow(
    onConnect: (robot: Robot) -> Unit,
    coroutineScope: CoroutineScope,
    onClose: () -> Unit,
    icon: ActionIcon,
    robotsContextImp: RobotsContextImp,
    connectionSpecificationList: ConnectionSpecificationList
) {
    val isEnable = remember { mutableStateOf(true) }

    ConnectionWindow(
        appIcon = icon,
        connectionSpecificationList = connectionSpecificationList,
        onCancel = onClose,
        onConnect = { selectedConnectionSpecification ->
            if (selectedConnectionSpecification != null) {
                coroutineScope.launch {
                    with(selectedConnectionSpecification) {
                        isEnable.value = false
                        robotsContextImp.connect(ip = ip, port = port, dataReadStatus = null,
                            onConnect = { robot ->
                                if (robot.isConnect.value) {
                                    onConnect(robot)
                                    onClose()
                                }
                            },
                            onConnectionError = {
                                isEnable.value = true
                            }
                        )
                    }
                }
            }
        },
        isEnable = isEnable
    )
}