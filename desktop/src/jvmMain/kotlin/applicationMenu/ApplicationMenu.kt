package applicationMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import plugin.Plugin
import view.ImagePluginView

@Composable
fun ApplicationMenu(
    pluginsMap: State<Map<String, Plugin>>,
    onAddPlugin: () -> Unit,
    onSelectPlugin: (name: String) -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val plugins by pluginsMap
    val iconSize = remember { 50.dp }

    Box(modifier.fillMaxHeight()) {
        Box(
            Modifier.size(iconSize).clickable {
                onGoHome()
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Home,
                "Home",
                modifier = Modifier.size(32.dp),
            )
        }

        Column(Modifier.align(Alignment.Center)) {
            plugins.values.toList().forEachIndexed { index, plugin ->
                Box(Modifier.padding(vertical = 10.dp).clickable {
                    onSelectPlugin(plugins.keys.elementAt(index))
                }) {
                    ImagePluginView(
                        modifier = Modifier.size(iconSize),
                        pluginImage = plugin.pluginInfo.pluginImage,
                        pluginName = plugin.pluginInfo.pluginName,
                        fontSize = 24.sp,
                        imagePadding = 10.dp,
                    )
                }
            }
        }



        Box(
            Modifier.align(Alignment.BottomCenter).size(iconSize).clickable {
                onAddPlugin()
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                "+",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}