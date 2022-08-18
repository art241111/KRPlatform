package windows.robotConnectionWindow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectionList(
    modifier: Modifier = Modifier,
    name: String,
    line1: String,
    line2: String,
    onFavorite: () -> Unit,
    onSelect: () -> Unit,
    onDoubleClick: () -> Unit,
    isEnable: Boolean,
) {
    Card(modifier.padding(10.dp).combinedClickable(enabled = isEnable, onDoubleClick = onDoubleClick) {
        onSelect()
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        ) {
            Column(Modifier.padding(10.dp)) {
                Text(name, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text(line1)
                Spacer(Modifier.height(3.dp))
                Text(line2)
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {
                    onFavorite()
                }
            ) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    null,
                    tint = Color(192, 0, 0),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}