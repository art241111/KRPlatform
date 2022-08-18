package ui.windows.connection.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.robotConnection.ConnectionList
import utils.ConnectionSpecification
import utils.ConnectionSpecificationList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    connectionSpecificationList: ConnectionSpecificationList,
    onConnect: (selectedConnectionSpecification: ConnectionSpecification?) -> Unit,
    isEnable: State<Boolean>,
    onAdd: () -> Unit
) {
    val list = connectionSpecificationList.connectSpecList.collectAsState()
    var selectedConnectionSpecification = remember<ConnectionSpecification?> { null }

    Box {
        LazyColumn(Modifier.fillMaxSize()) {
            items(list.value) {
                ConnectionList(
                    name = it.name,
                    line1 = "Ip: ${it.ip}",
                    line2 = "Port: ${it.port}",
                    onSelect = {
                        selectedConnectionSpecification = it
                    },
                    onFavorite = {},
                    onDoubleClick = {
                        selectedConnectionSpecification = it
                        onConnect(selectedConnectionSpecification)
                    },
                    isEnable = isEnable.value
                )
            }
            items(list.value) {
                Spacer(Modifier.height(10.dp))
            }
        }

        Row(modifier = Modifier.align(Alignment.BottomCenter).padding(10.dp)) {
            Button(
                onClick = { onConnect(selectedConnectionSpecification) },
                enabled = isEnable.value
            ) {
                Text("Подключиться", Modifier.padding(5.dp), fontSize = 20.sp)
            }

            Spacer(Modifier.width(20.dp))

            Card(
                onClick = {
                    onAdd()
                },
                backgroundColor = Color(192, 0, 0),
                shape = RoundedCornerShape(50),
                enabled = isEnable.value
            ) {
                Box(Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Add, "+")
                }
            }
        }
    }
}