package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import navigation.FilesScreen
import navigation.Navigation
import navigation.Screens
import plugin.PluginManager
import ui.views.actionIcon.ActionIcon
import ui.views.applicationMenu.ApplicationMenu
import ui.views.fileManager.DialogFile
import ui.windowView.Window
import ui.windows.main.screens.PluginManagerScreen
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter


@Composable
fun ApplicationScope.MainWindow(
    appIcon: ActionIcon,
    navigation: Navigation,
    coroutineScope: CoroutineScope,
    pluginManager: PluginManager,
    onClose: () -> Unit = ::exitApplication,
    onLoadFile: (file: File) -> Unit
) {
    val actualScreen by navigation.actualScreen.collectAsState()
    val actualSaveScreen by navigation.actualFileScreen.collectAsState()
    val actualPluginName by navigation.showPluginName.collectAsState()

    val plugins = pluginManager.plugins.collectAsState()

    Window(
        icon = appIcon,
        onClose = onClose,
        content = {
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

                when (actualSaveScreen) {
                    FilesScreen.LOAD_FILE -> {
                        DialogFile(
                            coroutineScope = coroutineScope,
                            scope = scope,
                            onResult = { files ->
                                if (files.isNotEmpty()) {
                                    onLoadFile(files[0])
                                }
                                navigation.backFromFileScreen()
                            }
                        )
                    }
                    FilesScreen.LOAD_PLUGIN -> {
                        DialogFile(
                            coroutineScope = coroutineScope,
                            scope = scope,
                            extensions = listOf(FileNameExtensionFilter("Plugin (.jar)", "jar")),
                            onResult = { files ->
                                if (files.isNotEmpty()) {
                                    files.forEach { file ->
                                        coroutineScope.launch(Dispatchers.IO) {
                                            pluginManager.loadPlugin(file)
                                        }
                                    }
                                }
                                navigation.backFromFileScreen()
                            }
                        )
                    }
                    else -> {}
                }


            }

        },
    )
}