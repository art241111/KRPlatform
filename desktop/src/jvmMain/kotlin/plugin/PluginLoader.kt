package plugin

import client.ClientsContext
import parameterContext.ParameterContextImpl
import robot.RobotsContext
import java.io.File
import java.net.MalformedURLException

class PluginLoader(
    private val robotsContext: RobotsContext,
    private val clientsContext: ClientsContext,
    private val parameterContext: ParameterContextImpl
) {
    fun loadPlugin(jar: File): Pair<String, Plugin>? {
        return try {
            val plugin = Plugin()
            val result = plugin.load(jar)
            if (result && plugin.plugin != null) {
                with(plugin.plugin!!) {
                    setRobotsContext(robotsContext)
                    setClientsContext(clientsContext)
                    setParameterContext(parameterContext.setName(plugin.pluginInfo.fileName))
                }
                plugin.pluginInfo.pluginImage = plugin.plugin!!.getPluginImage()
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