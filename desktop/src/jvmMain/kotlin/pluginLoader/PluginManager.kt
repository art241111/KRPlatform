package pluginLoader

import KRPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import robot.RobotsContext
import robotsContext.RobotsContextImp

class PluginManager(coroutineScope: CoroutineScope) {
    val plugins = MutableStateFlow(mapOf<String, KRPlugin>())

    private val pluginLoader = PluginLoader()
    private val robotsContext: RobotsContext = RobotsContextImp(coroutineScope)

    fun loadPlugins() {
        plugins.value = pluginLoader.load(
            robotsContext
        )
    }
}