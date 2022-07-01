package pluginLoader

import KRPlugin
import java.io.File
import java.net.URLClassLoader

class Plugin {
    var plugin: KRPlugin? = null
    private set
    val pluginInfo = PluginInfo()

    fun load(jarFile: File): Boolean {
        val result = pluginInfo.loadProperty(jarFile)
        if (result) {
            val jarURL = jarFile.toURI().toURL()
            val classLoader = URLClassLoader(arrayOf(jarURL))
            val pluginClass: Class<*> = classLoader.loadClass(pluginInfo.mainClassDir)
            plugin = pluginClass.getDeclaredConstructor().newInstance() as KRPlugin

        }
        return result
    }
}