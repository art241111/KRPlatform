package screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddPluginView(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
) {
    Button(onClick = onAdd, modifier) {
        Text("Add plugin")
    }
}