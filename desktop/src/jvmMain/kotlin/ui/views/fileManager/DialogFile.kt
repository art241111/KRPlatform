package ui.views.fileManager

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import kotlinx.coroutines.CoroutineScope
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


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
    val fileChooser = JFileChooser()
    fileChooser.dialogTitle = title
    fileChooser.isMultiSelectionEnabled = mode == Dialog.Mode.LOAD
    fileChooser.isAcceptAllFileFilterUsed = extensions.isEmpty()
    extensions.forEach { fileChooser.addChoosableFileFilter(it) }

    val returned = if (mode == Dialog.Mode.LOAD) {
        fileChooser.showOpenDialog(scope.window)
    } else {
        fileChooser.showSaveDialog(scope.window)
    }

    onResult(
        when (returned) {
            JFileChooser.APPROVE_OPTION -> {
                if (mode == Dialog.Mode.LOAD) {
                    val files = fileChooser.selectedFiles.filter { it.canRead() }
                    files.ifEmpty {
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
            else -> {
                listOf()
            }
        }
    )


//        this.cancel()
//    }

}