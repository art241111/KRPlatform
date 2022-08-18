package ui.views.textField

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleOutlinedTextField(
    onSet: (String) -> Unit,
    label: String
) {
    var directory by remember { mutableStateOf("") }
    OutlinedTextField(
        value = directory,
        onValueChange = {
            directory = it
        },
        modifier = Modifier.onPreviewKeyEvent {
            when {
                (it.key == Key.Enter) -> {
                    onSet(directory)
                    true
                }
                else -> false
            }
        },
        label = { Text(label) },
        singleLine = true,
    )
}