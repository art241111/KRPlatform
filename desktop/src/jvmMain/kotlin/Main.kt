// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.application
import data.AppIcons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navigation.Navigation
import navigation.Screens
import navigation.Windows
import plugin.PluginManager
import plugin.conetexts.clientContext.ClientsContextImp
import plugin.conetexts.filesContext.FilesContextImpl
import plugin.conetexts.robotsContext.RobotsContextImp
import robot.Robot
import ui.MainWindow
import ui.views.actionIcon.ActionIcon
import ui.views.fileManager.FileManager
import ui.views.textField.SingleOutlinedTextField
import ui.windows.robotConnection.RobotConnectionWindow
import utils.ConnectionSpecificationList
import java.io.File


fun main() = application {
    // File manager - is responsible for working with files located in AppData
    val fileManager = remember { FileManager("${System.getenv("APPDATA")}\\KRPlatform") }
    // Creating a basic coroutine scope
    val coroutineScope = rememberCoroutineScope()

    val connectionSpecificationList = ConnectionSpecificationList(fileManager, coroutineScope)

    // The variable stores the value of the new directory in which the plugin is located
    var pluginDir by remember { mutableStateOf("") }

    // Navigation - responsible for navigating the application
    val navigation = remember { Navigation() }


    val robot = remember { MutableStateFlow<Robot?>(null) }
    val robotsContext = remember {
        RobotsContextImp(
            coroutineScope,
            connectToRobotWindow = { retRobot ->
                coroutineScope.launch {
                    navigation.openConnectToRobotWindow()
                    robot.collectLatest {
                        if (it != null) {
                            val _robot = robot.value
                            robot.value = null
                            retRobot(_robot!!)
                            return@collectLatest
                        }
                    }
                }
            }
        )
    }

    val clientsContext = ClientsContextImp(coroutineScope)

    val fileLoad = remember { MutableStateFlow<File?>(null) }
    val filesContext = FilesContextImpl(
        coroutineScope,
        loadFileParam = {
            coroutineScope.launch {
                navigation.loadFile()
                fileLoad.collectLatest {
                    if (it != null) {
                        val _fileLoad = fileLoad.value
                        fileLoad.value = null
                        it(_fileLoad!!)
                        return@collectLatest
                    }
                }
            }
        }
    )

    // Plugin manager - responsible for working with plugins: downloading, deleting and updating
    val pluginManager = remember {
        PluginManager(
            coroutineScope = coroutineScope,
            localPluginDir = fileManager.localPluginDir,
            localParametersFile = fileManager.localParameterFile,
            robotsContext = robotsContext,
            clientsContext = clientsContext,
            filesContext = filesContext
        )
    }

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

    val scope = this
    // The main application window
    MainWindow(
        appIcon,
        navigation,
        coroutineScope,
        pluginManager,
        onClose = {
            robotsContext.disconnectAll()
            clientsContext.disconnectAll()
            scope.exitApplication()
        },
        onLoadFile = {
            fileLoad.value = it
        }
    )

    val windows = navigation.showAdditionalWindow.collectAsState()
    if (windows.value.contains(Windows.ROBOT_CONNECT)) {
        RobotConnectionWindow(
            icon = appIcon,
            onConnect = { _robot ->
                robot.value = _robot

            },
            coroutineScope = coroutineScope,
            onClose = {
                navigation.closeConnectToRobotWindow()
            },
            robotsContextImp = robotsContext,
            connectionSpecificationList = connectionSpecificationList
        )
    }
}
