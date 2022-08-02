package plugin

import client.ClientsContext
import clientContext.ClientsContextImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import robot.RobotsContext
import robotsContext.RobotsContextImp
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

private const val LOCAL_PLUGIN_DIR = "C:\\Users\\AG\\Desktop\\Platform\\KRPlatform\\desktop\\plugins"

class PluginManager(coroutineScope: CoroutineScope) {
    val plugins = MutableStateFlow(mutableMapOf<String, Plugin>())

    private val robotsContext: RobotsContext = RobotsContextImp(coroutineScope)
    private val clientsContext: ClientsContext = ClientsContextImp(coroutineScope)
    private val pluginLoader = PluginLoader(robotsContext, clientsContext)

    init {
        loadLocalPlugins()
    }

    fun loadLocalPlugins() {
        plugins.value = pluginLoader.loadPlugins(LOCAL_PLUGIN_DIR) as MutableMap<String, Plugin>
    }

    private fun loadPlugin(name: String) {
        val jar = File("$LOCAL_PLUGIN_DIR\\$name")
        val plug = pluginLoader.loadPlugin(jar)
        if (plug != null) {
            val m = plugins.value.toMutableMap()
            m[plug.first] = plug.second
            plugins.value = m
        }
    }

    fun loadPlugin(jar: File) {
        val localPluginDir = File("$LOCAL_PLUGIN_DIR\\${jar.name}")
        Files.copy(jar.toPath(), localPluginDir.toPath(), StandardCopyOption.REPLACE_EXISTING)

        val plug = pluginLoader.loadPlugin(localPluginDir)
        if (plug != null) {
            val m = plugins.value.toMutableMap()
            m[plug.first] = plug.second
            plugins.value = m
        }
    }

    fun loadPlugins(directory: String) {
        GlobalScope.launch {
            val pluginDir = File(directory)

            val jars: Array<File>? = pluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
            if (!jars.isNullOrEmpty()) {
                for (jar in jars) {
                    val jarName = jar.name


                    if (plugins.value.containsKey(jarName)) {
                        plugins.value[jarName]?.closePlugin()
                        val pl = plugins.value.toMutableMap()
                        pl.remove(jarName)
                        plugins.value = pl
                    }
                    delay(10L)
                    val localPluginDir = File("$LOCAL_PLUGIN_DIR\\${jarName}")
                    Files.copy(jar.toPath(), localPluginDir.toPath(), StandardCopyOption.REPLACE_EXISTING)

                    loadPlugin(jarName)

                }
            }
        }
    }
}