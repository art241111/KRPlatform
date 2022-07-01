package pluginLoader

import KRPlugin
import client.ClientsContext
import robot.RobotsContext
import java.io.File
import java.net.MalformedURLException

class PluginLoader {
    fun load(robotsContext: RobotsContext, clientsContext: ClientsContext): Map<String, KRPlugin> {
        val pluginDir = File("plugins")
        val pluginClasses: MutableMap<String, KRPlugin> = mutableMapOf()

        val jars: Array<File>? = pluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
        jars?.let { println(it.size) }
        if (!jars.isNullOrEmpty()) {
            for (jar in jars) {
                try {
                    val plugin = Plugin()
                    val result = plugin.load(jar)
                    if (result && plugin.plugin != null) {
                        plugin.plugin!!.setRobotsContext(robotsContext)
                        plugin.plugin!!.setClientsContext(clientsContext)
                        pluginClasses[plugin.pluginInfo.pluginName] = plugin.plugin!!
                    }
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

            }

        }
        return pluginClasses
    }
}