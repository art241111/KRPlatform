package view.ActionText

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
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
    height: Dp = 25.dp,
    contentColor: Color = contentColorFor(backgroundColor),
) {

    Card(
        modifier = Modifier
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