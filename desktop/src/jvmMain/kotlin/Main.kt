// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import data.AppIcons
import navigation.Navigation
import navigation.Screens
import plugin.PluginManager
import view.actionIcon.ActionIcon
import view.fileManager.FileManager
import view.textField.SingleOutlinedTextField
import windows.MainWindow


fun main() = application {
    // File manager - is responsible for working with files located in AppData
    val fileManager = remember { FileManager("${System.getenv("APPDATA")}\\KRPlatform") }

    // Creating a basic coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Plugin manager - responsible for working with plugins: downloading, deleting and updating
    val pluginManager = remember {
        PluginManager(
            coroutineScope = coroutineScope,
            localPluginDir = fileManager.localPluginDir,
            localParametersFile = fileManager.localParameterFile
        )
    }

    // The variable stores the value of the new directory in which the plugin is located
    var pluginDir by remember { mutableStateOf("") }

    // Navigation - responsible for navigating the application
    val navigation = remember { Navigation() }

    // Application icon at the top of the window
    val appIcon = ActionIcon(
        icon = painterResource(AppIcons.APP_ICON),
        description = "Robowizard",
        leftClick = {
            if (navigation.actualScreen.value == Screens.SHOW_PLUGIN) {
                pluginManager.updatePlugin(
                    pluginName = navigation.showPluginName.value,
                    pluginDir = pluginDir
                )
            }
        },
        rightClick = mapOf(
            Pair(
                first = {
                    SingleOutlinedTextField(
                        onSet = {
                            pluginDir = it
                        },
                        label = "Путь к плагину"
                    )
                },
                second = {}
            )
        )
    )

    // The main application window
    MainWindow(
        appIcon, navigation, coroutineScope, pluginManager
    )
}
