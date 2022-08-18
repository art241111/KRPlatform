package ui.windows.connection.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = port,
                onValueChange = {
                    port = when (it.toDoubleOrNull()) {
                        null -> {
                            port
                        }
                        else -> it
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Порт") },
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