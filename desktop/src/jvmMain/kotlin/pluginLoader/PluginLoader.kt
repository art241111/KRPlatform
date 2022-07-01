package pluginLoader

import KRPlugin
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.net.MalformedURLException
import javax.swing.JButton
import javax.swing.JFrame

class PluginLoader {
    val plugins = MutableStateFlow(mapOf<String, KRPlugin>())

    fun load() {
        val pluginDir = File("plugins")

        val jars: Array<File>? = pluginDir.listFiles { file -> file.isFile && file.name.endsWith(".jar") }
        jars?.let { println(it.size) }
        if (!jars.isNullOrEmpty()) {
            val pluginClasses: MutableMap<String, KRPlugin> = mutableMapOf()
            for (jar in jars) {

                try {
                    val plugin = Plugin()
                    val result = plugin.load(jar)
                    if (result) {
                        pluginClasses[plugin.pluginInfo.pluginName] = plugin.plugin!!
                    }

                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

            }
            plugins.value = pluginClasses
        }
    }
}