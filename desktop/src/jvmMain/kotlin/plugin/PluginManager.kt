package plugin

import client.ClientsContext
import clientContext.ClientsContextImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robot.RobotsContext
import robotsContext.RobotsContextImp
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class PluginManager(
    private val coroutineScope: CoroutineScope,
    private val localPluginDir: String
) {
    private val _plugins = MutableStateFlow(mapOf<String, Plugin>())
    val plugins: StateFlow<Map<String, Plugin>> = _plugins

    private val robotsContext: RobotsContext = RobotsContextImp(coroutineScope)
    private val clientsContext: ClientsContext = ClientsContextImp(coroutineScope)
    private val pluginLoader = PluginLoader(robotsContext, clientsContext)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            loadLocalPlugins()
        }
    }

    suspend fun loadLocalPlugins() {
        loadPlugins(localPluginDir)
    }

    private suspend fun removeContainsPlugin(jar: File) {
        val jarName = jar.name

        if (plugins.value.containsKey(jarName)) {
            plugins.value[jarName]?.closePlugin()
            val pl = plugins.value.toMutableMap()
            pl.remove(jarName)
            _plugins.value = pl
        }
        delay(10L)
    }

    suspend fun loadLocalPlugin(name: String) {
        val jar = File("$localPluginDir\\$name")
        removeContainsPlugin(jar)

        val plug = pluginLoader.loadPlugin(jar)
        if (plug != null) {
            val m = plugins.value.toMutableMap()
            m[plug.first] = plug.second
            _plugins.value = m
        }
    }

    //C:\Users\AG\Desktop\Platform\ReportCreator\build\libs\ReportCreator-jvm-1.0-SNAPSHOT.jar
    suspend fun loadPlugin(jar: File) {
        coroutineScope.launch(Dispatchers.IO) {
            val jarName = jar.name
            removeContainsPlugin(jar)

            try {
                val localPluginDir = File("$localPluginDir\\${jarName}")
                Files.copy(jar.toPath(), localPluginDir.toPath(), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: Exception) {
                print(e)
            }
            loadLocalPlugin(jarName)
        }

    }

    suspend fun loadPlugins(directory: String) {
        val pluginDir = File(directory)

        val jars: Array<File>? = pluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
        if (!jars.isNullOrEmpty()) {
            for (jar in jars) {
                loadPlugin(jar)
            }
        }
    }
}