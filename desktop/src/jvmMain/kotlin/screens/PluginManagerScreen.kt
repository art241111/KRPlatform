package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import plugin.Plugin

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PluginManagerScreen(
    pluginsMap: State<Map<String, Plugin>>,
    onAddPlugin: () -> Unit,
    onSelectPlugin: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val plugins by pluginsMap

    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 200.dp), modifier) {
        itemsIndexed(plugins.values.toList()) { index, plugin ->
            PluginView(
                pluginName = plugin.pluginInfo.pluginName,
                onSelect = {
                    onSelectPlugin(index)
                }
            )
        }

        item {
            AddPluginView {
                onAddPlugin()
            }
        }
    }
}