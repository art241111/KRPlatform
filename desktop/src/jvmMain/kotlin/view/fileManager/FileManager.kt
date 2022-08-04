package view.fileManager

import java.io.File
import java.nio.file.Files

private const val PLUGIN_DIR = "plugins"
private const val PARAMETER_DIR = "parameters"

class FileManager(
    localDir: String,
) {
    var localPluginDir = File("$localDir\\$PLUGIN_DIR")
        private set

    var localParameterFile = File("$localDir\\$PARAMETER_DIR")
        private set

    init {
        checkAndCreateParametersDir()
        checkAndCreatePluginDir()
    }

    private fun checkAndCreatePluginDir() {
        Files.createDirectories(localPluginDir.toPath().parent)
    }

    private fun checkAndCreateParametersDir() {
        localParameterFile.createNewFile()
    }
}