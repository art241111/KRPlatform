package ui.windows.main.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddPluginView(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
) {
    Card(
        modifier = modifier.padding(5.dp).clickable {
            onAdd()
        }.height(150.dp),
        elevation = 5.dp
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Add, "+", modifier = Modifier.size(60.dp))

                Spacer(Modifier.height(10.dp))
                Text("Добавить плагин")
            }
        }
    }
}