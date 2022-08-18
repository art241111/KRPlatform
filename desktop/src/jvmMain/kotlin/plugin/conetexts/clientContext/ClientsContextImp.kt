package plugin.conetexts.clientContext

import client.Client
import client.ClientsContext
import kotlinx.coroutines.CoroutineScope

class ClientsContextImp(private val coroutineScope: CoroutineScope) : ClientsContext {
    private val _clients = mutableListOf<Client>()
    override fun connect(): Client {
        TODO("Not yet implemented")
    }

    override fun connect(ip: String, port: Int): Client {
        var client = isConnected(ip, port)
        return if (client != null) {
            client
        } else {
            client = ClientImp(coroutineScope, ip, port)
            client.connect()
            _clients.add(client)
            client
        }

    }

    override fun disconnect() {
        disconnect(_clients.last() as ClientImp)
    }

    override fun disconnect(ip: String, port: Int, endMessage: String) {
        val _client = isConnected(ip, port)
        if (_client != null) {
            disconnect(_client as ClientImp, endMessage)
        }
    }

    private fun disconnect(client: ClientImp, endMessage: String = "") {
        client.disconnect(endMessage)
        _clients.remove(client)
    }

    fun disconnectAll() {
        _clients.forEach {
            (it as ClientImp).disconnect()
        }
        _clients.clear()
    }

    override fun isConnected(ip: String, port: Int): Client? {
        val client = _clients.filter { client -> client.ip == ip && client.port == port }

        return if (client.isEmpty()) {
            null
        } else {
            if (!client.first().isConnect.value) {
                _clients.remove(client.first())
                null
            } else {
                client.first()
            }
        }
    }
}