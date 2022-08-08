package parameterContext

import kotlinx.coroutines.CoroutineScope
import parameters.ParametersContext
import java.io.File
import java.nio.charset.Charset

private const val PARAMETER_SPL = "&&&"

class ParameterContextImpl(
    private val coroutineScope: CoroutineScope,
    private val saveFile: File,
) : ParametersContext {
    private var pluginName: String = ""

    override fun load(name: String): String {
        val properies = saveFile
            .readText(Charset.defaultCharset())
            .split(PARAMETER_SPL)
            .mapNotNull {
                Property.create(it)
            }
            .filter {
                it.name == name
            }
        return if (properies.isEmpty()) {
            ""
        } else {
            properies.first().property
        }
    }

    override fun save(name: String, content: String) {
        val properies = saveFile
            .readText(Charset.defaultCharset())
            .split(PARAMETER_SPL)
            .mapNotNull {
                Property.create(it)
            }
            .toMutableList()

        if (properies.none { it.name == name }) {
            properies.add(
                Property(
                    pluginName = pluginName,
                    name = name,
                    property = content
                )
            )
        } else {
            properies.replaceAll {
                if (it.name == name) {
                    Property(
                        name = it.name,
                        pluginName = it.pluginName,
                        property = content
                    )
                } else {
                    it
                }
            }
        }


        saveFile.writeText(properies.joinToString(PARAMETER_SPL))
    }

    fun setName(fileName: String): ParametersContext {
        pluginName = fileName
        return this
    }
}

class Property(
    val pluginName: String,
    val name: String,
    val property: String
) {
    override fun toString(): String {
        return "$pluginName$$$name::$property"
    }

    companion object {
        fun create(text: String): Property? {
            val split = text.split("::")
            var name = ""
            var property = ""
            var pluginName = ""
            if (split.size >= 2) {
                val names = split[0].split("$$")
                pluginName = names[0]
                name = names[1]
                property = split[1]
                return Property(pluginName, name, property)
            }
            return null
        }
    }
}