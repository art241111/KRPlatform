package plugin.conetexts.filesContext

import file.FilesContext
import kotlinx.coroutines.CoroutineScope
import java.io.File
import java.nio.charset.Charset

class FilesContextImpl(
    private val coroutineScope: CoroutineScope,
    private val loadFileParam: (onLoad: (File) -> Unit) -> Unit
) : FilesContext {
    override fun loadFile(onLoad: (String) -> Unit) {
        loadFileParam {
            val text = it.readText(Charset.defaultCharset())
            onLoad(text)
        }
    }

    override fun loadFile(fileName: String): String {
        return File(fileName).readText(Charset.defaultCharset())
    }

    override fun saveFile(text: String) {
    }

    override fun saveFile(fileName: String, text: String) {
        val file = File(fileName)
        file.createNewFile()
        file.writeText(text)
    }
}