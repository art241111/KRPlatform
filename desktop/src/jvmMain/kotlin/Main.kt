// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import applicationMenu.ApplicationMenu
import data.AppIcons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import plugin.PluginManager
import screens.PluginManagerScreen
import view.ActionIcon.ActionIcon
import view.fileManager.DialogFile
import window.Window
import java.io.File


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    val coroutineScope = rememberCoroutineScope()
    val pluginManager = remember {
        PluginManager(
            coroutineScope = coroutineScope,
            localPluginDir = "C:\\Users\\AG\\Desktop\\Platform\\KRPlatform\\desktop\\plugins"
        )
    }
    val plugins = pluginManager.plugins.collectAsState()
    val showPluginIndex = remember { mutableStateOf(-1) }
    var pluginDir by remember { mutableStateOf("") }

    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard",
        leftClick = {
            coroutineScope.launch(Dispatchers.IO) {
                if (showPluginIndex.value >= 0) {
                    if (pluginDir != "") {
                        pluginManager.loadPlugin(File(pluginDir))
                    } else {
                        val pluginList = plugins.value.values
                        pluginManager.loadLocalPlugin(pluginList.elementAt(showPluginIndex.value).pluginInfo.fileName)
                    }
                }
            }
        },
        rightClick = mapOf(
            Pair(
                first = {
                    var directory by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = directory,
                        onValueChange = {
                            directory = it
                        },
                        modifier = Modifier.onPreviewKeyEvent {
                            when {
                                (it.key == androidx.compose.ui.input.key.Key.Enter) -> {
                                    pluginDir = directory
                                    true
                                }
                                else -> false
                            }
                        },
                        label = { Text("Путь к плагину") },
                        singleLine = true,
                    )
                },
                second = {}
            )
        )
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
                                    coroutineScope.launch(Dispatchers.IO) {
                                        pluginManager.loadPlugin(file)
                                    }
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
                    if (plugins.value.values.size > showPluginIndex.value) {
                        plugins.value.values.toList()[showPluginIndex.value].plugin?.content()
                    }
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
