package plugin

import client.ClientsContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import plugin.conetexts.clientContext.ClientsContextImp
import plugin.conetexts.parameterContext.ParameterContextImpl
import robot.RobotsContext
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class PluginManager(
    private val coroutineScope: CoroutineScope,
    private val localPluginDir: File,
    localParametersFile: File,
    robotsContext: RobotsContext,
    clientsContext: ClientsContext = ClientsContextImp(coroutineScope),
    parameterContext: ParameterContextImpl = ParameterContextImpl(coroutineScope, localParametersFile),
    private val pluginLoader: PluginLoader = PluginLoader(robotsContext, clientsContext, parameterContext)
) {
    private val _plugins = MutableStateFlow(mapOf<String, Plugin>())
    val plugins: StateFlow<Map<String, Plugin>> = _plugins

    init {
        coroutineScope.launch(Dispatchers.IO) {
            loadLocalPlugins()
        }
    }

    fun updatePlugin(pluginName: String, pluginDir: String = "") {
        coroutineScope.launch(Dispatchers.IO) {
            if (pluginDir != "") {
                loadPlugin(File(pluginDir))
            } else {
                val pluginList = plugins.value
                pluginList[pluginName]?.pluginInfo?.let { loadLocalPlugin(it.fileName) }
            }
        }
    }

    private suspend fun loadLocalPlugins() {
        val jars: Array<File>? = localPluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
        if (!jars.isNullOrEmpty()) {
            for (jar in jars) {
                loadLocalPlugin(jar.name)
            }
        }
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
        val jar = File("${localPluginDir.toPath()}\\$name")
        removeContainsPlugin(jar)

        val plug = pluginLoader.loadPlugin(jar)
        if (plug != null) {
            val m = plugins.value.toMutableMap()
            m[plug.first] = plug.second
            _plugins.value = m
        }
    }

    suspend fun loadPlugin(jar: File) {
        val jarName = jar.name
        removeContainsPlugin(jar)

        val localPluginDir = File("${localPluginDir.toPath()}\\${jarName}")
        Files.createDirectories(localPluginDir.toPath().parent)
        Files.copy(jar.toPath(), localPluginDir.toPath(), StandardCopyOption.REPLACE_EXISTING)
        loadLocalPlugin(jarName)
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