package view.ActionIcon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.SystemColor.text

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActionIconView(
    actionIcon: ActionIcon,
    onClick: () -> Unit = {},
    width: Dp = 45.dp,
    height: Dp = 25.dp,
    contentColor: Color = contentColorFor(backgroundColor),
    selectColor: Color = Color.LightGray,
    iconPadding: Dp = 9.dp
) {
    var expand by remember { mutableStateOf(false) }
    DropdownMenu(expanded = expand, onDismissRequest = { expand = false }) {
        for ((content, action) in actionIcon.rightClick) {
            DropdownMenuItem(
                onClick = action
            ) {
                content()
            }
        }
    }
    var active by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxHeight()
            .size(width = width, height = height)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .onPointerEvent(PointerEventType.Press) {
                when {
                    it.buttons.isSecondaryPressed -> {
                        expand = true
                    }
                    it.buttons.isPrimaryPressed -> {
                        actionIcon.leftClick()
                        onClick()
                    }
                    else -> text
                }
            },
        shape = RectangleShape,
        border = BorderStroke(0.dp, color = MaterialTheme.colors.background),
        elevation = 0.dp,
        contentColor = contentColor,
        backgroundColor = if (active) selectColor else Color.Transparent
    ) {
        Box(modifier = Modifier.size(8.dp).padding(iconPadding), contentAlignment = Alignment.Center) {
            Icon(
                painter = actionIcon.icon,
                contentDescription = actionIcon.description
            )
        }
    }
}