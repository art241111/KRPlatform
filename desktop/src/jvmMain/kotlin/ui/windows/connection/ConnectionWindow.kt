package ui.robotConnection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import ui.views.actionIcon.ActionIcon
import ui.windowView.Window
import ui.windows.connection.entity.ConnectionStatus
import ui.windows.connection.screens.AddScreen
import ui.windows.connection.screens.ListScreen
import ui.windows.connection.views.BackgroundScreen
import utils.ConnectionSpecification
import utils.ConnectionSpecificationList

@Composable
fun ApplicationScope.ConnectionWindow(
    connectionSpecificationList: ConnectionSpecificationList,
    onConnect: (selectedConnectionSpecification: ConnectionSpecification?) -> Unit,
    onCancel: () -> Unit,
    appIcon: ActionIcon,
    isEnable: State<Boolean>,
) {
    val connectionStatus = remember { mutableStateOf(ConnectionStatus.CONNECTION) }
    Window(
        icon = appIcon,
        onClose = onCancel,
        size = DpSize(400.dp, 700.dp),
        alwaysOnTop = true,
        background = Color(192, 0, 0),
        contentColor = Color.White
    ) {
        BackgroundScreen {
            when (connectionStatus.value) {
                ConnectionStatus.CONNECTION -> {
                    ListScreen(
                        connectionSpecificationList = connectionSpecificationList,
                        onConnect = onConnect,
                        isEnable = isEnable,
                        onAdd = {
                            connectionStatus.value = ConnectionStatus.ADD
                        }
                    )
                }

                ConnectionStatus.ADD -> {
                    AddScreen(
                        onBack = {
                            connectionStatus.value = ConnectionStatus.CONNECTION
                        },
                        onAdd = { name, ip, port ->
                            connectionSpecificationList.add(
                                ConnectionSpecification(
                                    name = name,
                                    ip = ip,
                                    port = port
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}