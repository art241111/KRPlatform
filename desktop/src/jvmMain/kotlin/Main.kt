// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import applicationMenu.ApplicationMenu
import com.robowizard.common.App
import data.AppIcons
import view.ActionIcon.ActionIcon
import window.Window


fun main() = application {
    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard"
    )

    Window(
        icon = appIcon
    ) {
        Row {
            ApplicationMenu(
                menuItems = listOf(),
            )
            App()
        }

    }
}
