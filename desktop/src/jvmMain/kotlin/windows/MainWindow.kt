package windows

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ApplicationScope
import applicationMenu.ApplicationMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import navigation.Navigation
import navigation.Screens
import plugin.PluginManager
import screens.PluginManagerScreen
import view.actionIcon.ActionIcon
import view.fileManager.DialogFile
import windowView.Window


@Composable
fun ApplicationScope.MainWindow(
    appIcon: ActionIcon,
    navigation: Navigation,
    coroutineScope: CoroutineScope,
    pluginManager: PluginManager,
) {
    val actualScreen by navigation.actualScreen.collectAsState()
    val actualPluginName by navigation.showPluginName.collectAsState()

    val plugins = pluginManager.plugins.collectAsState()

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