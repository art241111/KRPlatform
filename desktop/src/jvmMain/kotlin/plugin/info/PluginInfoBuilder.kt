package plugin.info

import java.io.File
import java.io.InputStream
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

object PluginInfoBuilder {
    fun load(file: File): PluginInfo? {
        var result: Properties? = null
        val jar = JarFile(file)
        val entries = jar.entries()

        val fileName: String = file.name
        var mainClassDir = ""
        var pluginName = ""

        while (entries.hasMoreElements()) {
            val entry: JarEntry = entries.nextElement()
            if (entry.name.equals("plugin.properties")) {
                // That's it! Load props
                var `is`: InputStream? = null
                try {
                    `is` = jar.getInputStream(entry)
                    result = Properties()
                    result.load(`is`)
                } finally {
                    `is`?.close()
                }
            }
        }

        val props = result
        requireNotNull(props) { "No props file found" }

        val pluginClassName = props.getProperty("main.class")
        if (pluginClassName == null || pluginClassName.isEmpty()) {
//            throw PluginLoadException("Missing property main.class")
            println("Missing property main.class")
            return null
        } else {
            mainClassDir = pluginClassName
        }

//        val pluginName = props.getProperty("plugin.name")
//        if (pluginName == null || pluginName.isEmpty()) {
////            throw PluginLoadException("Missing property button.text")
//            println("Missing property pluginName")
//            return false
//        } else {
//            this.pluginName = pluginName
//        }
        pluginName = "pluginName"

        jar.close()

        return PluginInfo(
            fileName = fileName,
            mainClassDir = mainClassDir,
            pluginName = pluginName
        )
    }
}
