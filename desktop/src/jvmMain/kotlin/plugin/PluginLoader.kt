package plugin

import client.ClientsContext
import robot.RobotsContext
import java.io.File
import java.net.MalformedURLException

class PluginLoader(
    private val robotsContext: RobotsContext,
    private val clientsContext: ClientsContext
) {
    fun loadPlugins(localDur: String): Map<String, Plugin> {
        val pluginDir = File(localDur)
        val pluginClasses: MutableMap<String, Plugin> = mutableMapOf()

        val jars: Array<File>? = pluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
        jars?.let { println(it.size) }
        if (!jars.isNullOrEmpty()) {
            for (jar in jars) {
                val plug = loadPlugin(jar)
                if (plug != null) {
                    pluginClasses[plug.first] = plug.second
                }
            }
        }
        return pluginClasses
    }

    fun loadPlugin(jar: File): Pair<String, Plugin>? {
        return try {
            val plugin = Plugin()
            val result = plugin.load(jar)
            if (result && plugin.plugin != null) {
                plugin.plugin!!.setRobotsContext(robotsContext)
                plugin.plugin!!.setClientsContext(clientsContext)
                return Pair(plugin.pluginInfo.fileName, plugin)
            } else {
                null
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}