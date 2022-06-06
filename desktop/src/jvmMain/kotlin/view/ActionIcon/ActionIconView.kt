package view.ActionIcon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionIconView(
    actionIcon: ActionIcon,
    onClick: () -> Unit = {},
    width: Dp = 45.dp,
    height: Dp = 25.dp,
    contentColor: Color = contentColorFor(backgroundColor),
    iconPadding: Dp = 7.dp
) {

    Card(
        modifier = Modifier
            .size(width = width, height = height)
            .clickable {
                actionIcon.leftClick()
                onClick()
            },
        shape = RectangleShape,
        border = BorderStroke(0.dp, color = MaterialTheme.colors.background),
        elevation = 0.dp,
        contentColor = contentColor
    ) {
        Icon(
            modifier = Modifier.padding(iconPadding),
            painter = actionIcon.icon,
            contentDescription = actionIcon.description
        )
    }
}