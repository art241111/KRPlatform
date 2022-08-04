package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import plugin.info.PluginInfo

@Composable
fun PluginView(
    pluginInfo: PluginInfo,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(5.dp).clickable {
            onSelect()
        }.height(150.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val bit = pluginInfo.pluginImage
            if (bit != null) {
                Image(bit, contentDescription = "", modifier = Modifier.size(100.dp))
            }

            Spacer(Modifier.height(10.dp))
            Text(pluginInfo.pluginName)
        }
    }
}