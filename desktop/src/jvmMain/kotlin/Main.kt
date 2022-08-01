// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import applicationMenu.ApplicationMenu
import data.AppIcons
import plugin.PluginManager
import view.ActionIcon.ActionIcon
import window.Window


fun main() = application {


    val coroutineScope = rememberCoroutineScope()
    val pluginManager = remember { PluginManager(coroutineScope) }
    val plugins = pluginManager.plugins.collectAsState()

    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard",
        leftClick = {
            pluginManager.loadPlugins("C:\\Users\\AG\\Desktop\\Platform\\KRobotHandler\\build\\libs")
        }
    )
    Window(
        icon = appIcon,
    ) {
        Row {
            ApplicationMenu(
                menuItems = listOf(),
            )

            Column(Modifier.fillMaxSize()) {
                for ((name, value) in plugins.value) {
                    value.plugin?.content()
                }
            }
        }

    }
}
