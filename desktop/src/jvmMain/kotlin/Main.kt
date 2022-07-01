// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import applicationMenu.ApplicationMenu
import com.robowizard.common.App
import data.AppIcons
import pluginLoader.PluginLoader
import view.ActionIcon.ActionIcon
import window.Window


fun main() = application {
    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard"
    )

    val pluginLoader = remember { PluginLoader() }
    val plugins = pluginLoader.plugins.collectAsState()
    Window(
        icon = appIcon
    ) {
        Row {
            ApplicationMenu(
                menuItems = listOf(),
            )
            Button(
                onClick = {
                    pluginLoader.load()
                    println(pluginLoader.plugins.value.size)
                }
            ) {
                Text("Loads plugins")
            }


            for ((key, value) in plugins.value) {
                value.content()
            }

            App()
        }

    }
}
