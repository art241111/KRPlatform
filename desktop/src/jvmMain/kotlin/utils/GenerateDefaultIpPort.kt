package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import view.fileManager.FileManager

object GenerateDefaultIpPort {
    fun generate() = listOf(
        ConnectionSpecification("K-ROSET 1", "127.0.0.1", 9105),
        ConnectionSpecification("K-ROSET 2", "127.0.0.1", 9205),
        ConnectionSpecification("K-ROSET 3", "127.0.0.1", 9305),
        ConnectionSpecification("Standard 1", "192.168.0.2", 23),
        ConnectionSpecification("Standard 2", "192.168.11.2", 23),
        ConnectionSpecification("Bluetooth", "10.1.2.3", 23),

        )
}


class ConnectionSpecificationList(
    fileManager: FileManager,
    private val coroutineScope: CoroutineScope
) {
    private val file = fileManager.localRobotSpecFile

    private val _connectSpecList = MutableStateFlow(GenerateDefaultIpPort.generate())
    val connectSpecList: StateFlow<List<ConnectionSpecification>> = _connectSpecList

    init {
        coroutineScope.launch {
            val text = file.readText()
            if (text.isNotEmpty()) {
                val mutableList = mutableListOf<ConnectionSpecification>()
                mutableList.addAll(
                    text.split("\n").map {
                        ConnectionSpecification.getFromString(it)
                    }
                )
                _connectSpecList.value = mutableList.toList()
            }
        }
    }

    fun add(specification: ConnectionSpecification) {
        val list = _connectSpecList.value.toMutableList()
        list.add(specification)
        _connectSpecList.value = list
    }

    fun remove(specification: ConnectionSpecification) {
        val list = _connectSpecList.value.toMutableList()
        list.remove(specification)
        _connectSpecList.value = list
    }

    fun save() {
        coroutineScope.launch {
            file.writeText(
                _connectSpecList.value.joinToString(separator = "\n")
            )
        }
    }

}

data class ConnectionSpecification(
    val name: String,
    val ip: String,
    val port: Int,
    val isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false),
) {
    override fun toString(): String {
        return "$name;$ip;$port;${isFavorite.value}"
    }

    companion object {
        fun getFromString(line: String): ConnectionSpecification {
            val spl = line.split(";")
            return ConnectionSpecification(
                spl[0],
                spl[1],
                spl[2].toInt(),
                MutableStateFlow(spl[3].toBooleanStrict())
            )
        }
    }
}