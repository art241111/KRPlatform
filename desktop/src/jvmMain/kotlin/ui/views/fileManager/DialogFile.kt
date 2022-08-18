package ui.views.fileManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.FrameWindowScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.coroutines.CoroutineContext

class Dialog {
    enum class Mode { LOAD, SAVE }
}

@Composable
fun DialogFile(
    coroutineScope: CoroutineScope,
    mode: Dialog.Mode = Dialog.Mode.LOAD,
    title: String = "Choisissez un fichier",
    extensions: List<FileNameExtensionFilter> = listOf(),
    scope: FrameWindowScope,
    onResult: (files: List<File>) -> Unit
) {
    DisposableEffect(Unit) {
        val job = coroutineScope.launch(Dispatchers.Swing as CoroutineContext) {
            val fileChooser = JFileChooser()
            fileChooser.dialogTitle = title
            fileChooser.isMultiSelectionEnabled = mode == Dialog.Mode.LOAD
            fileChooser.isAcceptAllFileFilterUsed = extensions.isEmpty()
            extensions.forEach { fileChooser.addChoosableFileFilter(it) }

            val returned =

                when (if (mode == Dialog.Mode.LOAD) {
                    fileChooser.showOpenDialog(scope.window)
                } else {
                    fileChooser.showSaveDialog(scope.window)
                })
                {
                    JFileChooser.APPROVE_OPTION -> {
                    if (mode == Dialog.Mode.LOAD) {
                        val files = fileChooser.selectedFiles.filter { it.canRead() }
                        if (files.isNotEmpty()) {
                            files
                        } else {
                            listOf()
                        }
                    } else {
                        if (!fileChooser.fileFilter.accept(fileChooser.selectedFile)) {
                            val ext = (fileChooser.fileFilter as FileNameExtensionFilter).extensions[0]
                            fileChooser.selectedFile = File(fileChooser.selectedFile.absolutePath + ".$ext")
                        }
                        listOf(fileChooser.selectedFile)
                    }
                }
                    else -> listOf()
                })
        }

        onDispose {
            job.cancel()
        }
    }
}