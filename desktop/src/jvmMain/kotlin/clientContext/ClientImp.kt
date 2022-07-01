package clientContext

import client.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientImp(
    val coroutineScope: CoroutineScope,
    override val ip: String = "197.0.0.1",
    override val port: Int = 9
) : Client {
    val client = com.github.art241111.tcpClient.Client(coroutineScope)

    override val dataHandler: SharedFlow<String> = client.incomingText
    override val isConnect: StateFlow<Boolean> = client.isConnect

    override fun connect() {
        coroutineScope.launch(Dispatchers.IO) {
            client.connect(ip, port)
        }
    }

    override fun disconnect() {
        client.disconnect()
    }

    override fun send(message: String) {
        client.send(message)
    }
}