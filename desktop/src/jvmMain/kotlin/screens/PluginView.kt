package screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import plugin.info.PluginInfo
import view.ImagePluginView

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
            ImagePluginView(
                modifier = Modifier.weight(1f),
                pluginImage = pluginInfo.pluginImage,
                pluginName = pluginInfo.pluginName,
            )

            Spacer(Modifier.height(10.dp))
            Text(pluginInfo.pluginName)
        }
    }
}