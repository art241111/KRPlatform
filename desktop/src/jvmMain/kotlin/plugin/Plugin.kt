package plugin

import KRPlugin
import plugin.info.PluginInfo
import plugin.info.PluginInfoBuilder
import java.io.File
import java.net.URLClassLoader

class Plugin {
    var plugin: KRPlugin? = null
        private set
    var pluginInfo = PluginInfo()
        private set

    var classLoader: URLClassLoader? = null

    fun load(jarFile: File): Boolean {
        val result = PluginInfoBuilder.load(jarFile)
        if (result != null) {
            pluginInfo = result
            val jarURL = jarFile.toURI().toURL()
            classLoader = URLClassLoader(arrayOf(jarURL))
            if (classLoader != null) {
                val pluginClass: Class<*> = classLoader!!.loadClass(pluginInfo.mainClassDir)
                plugin = pluginClass.getDeclaredConstructor().newInstance() as KRPlugin
            }
        }
        return result != null
    }

    fun closePlugin() {
        classLoader?.close()
    }
}