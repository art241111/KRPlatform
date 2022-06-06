package view.ActionText

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionTextView(
    actionText: ActionText,
    onClick: () -> Unit = {},
    width: Dp = 45.dp,
    height: Dp = 25.dp,
    contentColor: Color = contentColorFor(backgroundColor),
) {

    Card(
        modifier = Modifier
            .size(width = width, height = height)
            .clickable {
                actionText.onClick()
                onClick()
            },
        shape = RectangleShape,
        border = BorderStroke(0.dp, color = MaterialTheme.colors.background),
        elevation = 0.dp,
        contentColor = contentColor
    ) {
        Text(
            text = actionText.name
        )
    }
}