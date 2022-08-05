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
import navigation.Navigation
import navigation.Screens
import plugin.PluginManager
import screens.PluginManagerScreen
import view.actionIcon.ActionIcon
import view.fileManager.DialogFile
import view.fileManager.FileManager
import windowView.Window
import java.io.File


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val fileManager = remember { FileManager("${System.getenv("APPDATA")}\\KRPlatform") }
    val coroutineScope = rememberCoroutineScope()
    val pluginManager = remember {
        PluginManager(
            coroutineScope = coroutineScope,
            localPluginDir = fileManager.localPluginDir,
            localParametersFile = fileManager.localParameterFile
        )
    }
    val plugins = pluginManager.plugins.collectAsState()
    var pluginDir by remember { mutableStateOf("") }

    val navigation = remember { Navigation() }
    val actualPluginName by navigation.showPluginName.collectAsState()
    val actualScreen by navigation.actualScreen.collectAsState()

    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard",
        leftClick = {
            coroutineScope.launch(Dispatchers.IO) {
//                if (showPluginIndex.value >= 0) {
                if (pluginDir != "") {
                    pluginManager.loadPlugin(File(pluginDir))
                } else {
                    val pluginList = plugins.value
                    pluginList[actualPluginName]?.pluginInfo?.let { pluginManager.loadLocalPlugin(it.fileName) }
                }
//                }
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
                pluginsMap = plugins,
                onAddPlugin = {
                    navigation.loadPlugin()
                },
                onSelectPlugin = { pluginName ->
                    navigation.showPlugin(pluginName)
                },
                onGoHome = {
                    navigation.goHome()
                }
            )

            when (actualScreen) {
                Screens.LOAD_PLUGIN -> {
                    DialogFile(
                        scope = scope,
                        onResult = { files ->
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
                Screens.HOME -> {
                    PluginManagerScreen(
                        pluginsMap = plugins,
                        onAddPlugin = {
                            navigation.loadPlugin()
                        },
                        onSelectPlugin = { pluginName ->
                            navigation.showPlugin(pluginName)
                        }
                    )
                }
                Screens.SHOW_PLUGIN -> {
                    val pluginsList = plugins.value

                    if (pluginsList.containsKey(actualPluginName)) {
                        plugins.value[actualPluginName]?.plugin?.content()
                    }
                }
            }

        }

    }
}
