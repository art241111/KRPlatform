package ui.windows.connection.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.AppIcons

@Composable
fun BackgroundScreen(
    content: @Composable () -> Unit,
) {
    Box(Modifier.background(Color(192, 0, 0))) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(10.dp))
            Icon(
                modifier = Modifier.size(width = 40.dp, height = 52.dp),
                painter = painterResource(AppIcons.APP_ICON),
                contentDescription = "RW",
                tint = Color.White
            )
            Spacer(Modifier.height(10.dp))
            Text("ПОДКЛЮЧЕНИЕ К РОБОТУ", color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(15.dp))


            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 33f, topEnd = 33f),
                backgroundColor = Color.White
            ) {
                content()
            }
        }
    }
}