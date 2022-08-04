package applicationMenu

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import plugin.Plugin
import screens.PluginView

@Composable
fun ApplicationMenu(
    pluginsMap: State<Map<String, Plugin>>,
    onAddPlugin: () -> Unit,
    onSelectPlugin: (name: String) -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val plugins by pluginsMap

    LazyColumn(modifier) {
        item {
            IconButton(
                onClick = onGoHome
            ) {
                Icon(
                    Icons.Default.Home,
                    "Home",
                    modifier = Modifier.size(64.dp),
                    tint = Color.DarkGray
                )
            }
        }

        itemsIndexed(plugins.values.toList()) { index, plugin ->
            PluginView(
                modifier = Modifier.size(64.dp),
                pluginInfo = plugin.pluginInfo,
                onSelect = {
                    onSelectPlugin(plugins.keys.elementAt(index))
                }
            )
        }

        item {
            IconButton(
                onClick = onAddPlugin
            ) {
                Icon(
                    Icons.Default.Add,
                    "+",
                    modifier = Modifier.size(64.dp),
                    tint = Color.DarkGray
                )
            }
        }
    }
}