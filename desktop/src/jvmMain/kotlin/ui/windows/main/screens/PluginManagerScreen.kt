package ui.windows.main.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
    onSelectPlugin: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val plugins by pluginsMap

    Box(modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 200.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            itemsIndexed(plugins.values.toList()) { index, plugin ->
                PluginView(
                    pluginInfo = plugin.pluginInfo,
                    onSelect = {
                        onSelectPlugin(plugins.keys.elementAt(index))
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
}