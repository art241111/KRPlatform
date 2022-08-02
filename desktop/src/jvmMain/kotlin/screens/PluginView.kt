package screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PluginView(
    pluginName: String,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onSelect, modifier) {
        Text(pluginName)
    }
}