package windows.connectionWindow.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddScreen(
    onBack: () -> Unit,
    onAdd: (name: String, ip: String, port: Int) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("") }
    Box {
        Column(Modifier.padding(10.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { text ->
                    name = text
                },
                label = { Text("Имя робота") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ip,
                onValueChange = { text ->
                    ip = text
                },
                label = { Text("Ip адрес") },
                singleLine = true
            )

            // TODO: Only numbers
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = port,
                onValueChange = { text ->
                    port = text
                },
                label = { Text("Порт") },
                singleLine = true
            )

        }
        Row(modifier = Modifier.padding(10.dp).align(Alignment.BottomCenter)) {
            Button(
                onClick = {
                    onAdd(name, ip, port.toInt())
                    onBack()
                },
            ) {
                Text("Добавить робота", Modifier.padding(5.dp), fontSize = 20.sp)
            }

            Button(
                onClick = {
                    onBack()
                }
            ) {
                Text("Отмена", Modifier.padding(5.dp), fontSize = 20.sp)
            }
        }
    }
}