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
import view.fileManager.FileManager
import window.Window
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
    val showPluginName = remember { mutableStateOf("home") }
    var pluginDir by remember { mutableStateOf("") }

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
                        pluginList[showPluginName.value]?.pluginInfo?.let { pluginManager.loadLocalPlugin(it.fileName) }
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
                menuItems = listOf(),
            )

            when (showPluginName.value) {
                "loadFiles" -> {
                    DialogFile(
                        scope = scope,
                        onResult = { files ->
                            showPluginName.value = "home"

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
                "home" -> {
                    PluginManagerScreen(
                        pluginsMap = plugins,
                        onAddPlugin = {
                            showPluginName.value = "loadFiles"
                        },
                        onSelectPlugin = {
                            showPluginName.value = it
                        }
                    )
                }
                else -> {
                    val pluginsList = plugins.value

                    if (pluginsList.containsKey(showPluginName.value)) {
                        plugins.value[showPluginName.value]?.plugin?.content()
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
