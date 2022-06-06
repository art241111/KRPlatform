package applicationMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import view.ActionIcon.ActionIcon
import view.ActionIcon.ActionIconView

@Composable
fun ApplicationMenu (
    modifier: Modifier = Modifier,
    menuItems: List<ActionIcon> = listOf()
){
    Column (modifier) {
        menuItems.forEach {
            ActionIconView(it)
        }
    }
}