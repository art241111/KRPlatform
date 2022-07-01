package clientContext

import client.Client
import client.ClientsContext
import kotlinx.coroutines.CoroutineScope

class ClientsContextImp(private val coroutineScope: CoroutineScope) : ClientsContext {
    private val _client = mutableMapOf<String, Client>()

    override fun addClient(name: String, ip: String, port: Int) {
        _client[name] = ClientImp(coroutineScope, ip, port)
    }

    override fun changeClientName(oldName: String, newName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteClient(name: String) {
        _client.remove(name)
    }

    override fun getClient(name: String): Client {
        return _client[name]!!
    }

    override fun getClientsName(): List<String> {
        return _client.keys.toList()
    }

}