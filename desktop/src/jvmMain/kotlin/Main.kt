// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import applicationMenu.ApplicationMenu
import data.AppIcons
import plugin.PluginManager
import screens.PluginManagerScreen
import view.ActionIcon.ActionIcon
import view.fileManager.DialogFile
import window.Window


fun main() = application {

    val coroutineScope = rememberCoroutineScope()
    val pluginManager = remember { PluginManager(coroutineScope) }
    val plugins = pluginManager.plugins.collectAsState()
    val showPluginIndex = remember { mutableStateOf(-1) }

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
        val scope = it
        Row {
            ApplicationMenu(
                menuItems = listOf(),
            )

            when (showPluginIndex.value) {
                -2 -> {
                    DialogFile(
                        scope = scope,
                        onResult = { files ->
                            showPluginIndex.value = -1

                            if (files.isNotEmpty()) {
                                files.forEach { file ->
                                    pluginManager.loadPlugin(file)
                                }
                            }
                        }
                    )
                }
                -1 -> {
                    PluginManagerScreen(
                        pluginsMap = plugins,
                        onAddPlugin = {
                            showPluginIndex.value = -2
                        },
                        onSelectPlugin = {
                            showPluginIndex.value = it
                        }
                    )
                }
                else -> {
                    plugins.value.values.toList()[showPluginIndex.value].plugin?.content()
                }
            }


//            Column(Modifier.fillMaxSize()) {
//                for ((name, value) in plugins.value) {
//                    value.plugin?.content()
//                }
//            }
        }

    }
}
