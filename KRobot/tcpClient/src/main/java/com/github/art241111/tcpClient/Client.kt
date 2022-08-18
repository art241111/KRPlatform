package com.github.art241111.tcpClient

import com.github.art241111.tcpClient.connection.Status
import com.github.art241111.tcpClient.connection.TCPConnection
import com.github.art241111.tcpClient.reader.DefaultRemoteReader
import com.github.art241111.tcpClient.reader.RemoteReader
import com.github.art241111.tcpClient.writer.DefaultRemoteWriter
import com.github.art241111.tcpClient.writer.RemoteWriter
import com.github.art241111.tcpClient.writer.Sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * TCP client.
 * @author Artem Gerasimov.
 */
open class Client(private val coroutineScope: CoroutineScope) : Sender {
    private val connection = TCPConnection()
    private val remoteReader: RemoteReader = DefaultRemoteReader()
    private val remoteWriter: RemoteWriter = DefaultRemoteWriter()

    val incomingText = remoteReader.incomingText
    val incomingLetter = remoteReader.incomingLetter

    /**
     * @return connect status
     */
    val statusState: StateFlow<Status?> = connection.statusState

    // Connection parameters. Saved to use when reconnecting
    private var oldAddress = ""
    private var oldPort = -1
    private var isWriterLogging = false
    private var isReaderLogging = false

    private var _isConnect = MutableStateFlow(false)
    val isConnect: StateFlow<Boolean> = _isConnect

    /**
     * Connect to TCP sever.
     * @param address - server ip port,
     * @param port - server port,
     * @param isWriterLogging - will the output of the send data occur,
     * @param isReaderLogging - will the output of the received data occur.
     */
    suspend fun connect(
        address: String,
        port: Int,
        timeout: Int = 2000,
        isWriterLogging: Boolean = false,
        isReaderLogging: Boolean = false,
        defaultMessage: String = "",
        onConnectionError: (e: Exception) -> Unit = {}
    ) {
        oldAddress = address
        oldPort = port
        this.isWriterLogging = isWriterLogging
        this.isReaderLogging = isReaderLogging

        // When the device connects to the server, it creates Reader and Writer
        coroutineScope.launch(Dispatchers.IO) {
            connection.connect(address, port, timeout, onConnectionError)
            createReaderAndWriter(isWriterLogging, isReaderLogging)
            _isConnect.value = true

            if (defaultMessage.isNotEmpty()) send(defaultMessage)
        }.join()
    }

    /**
     * Disconnect from TCP sever.
     *
     * @param stopSymbol - the character that is sent when disconnecting from the server.
     */
    fun disconnect(stopSymbol: String = "") {
        if (statusState.value != Status.DISCONNECTED) {
            remoteWriter.destroyWriter(stopSymbol)
            _isConnect.value = false

            coroutineScope.launch {
                delay(50L)
                connection.disconnect()
            }
            remoteReader.destroyReader()
        }
    }

    /**
     * Send text to the server.
     */
    override fun send(text: String) {
        if (statusState.value == Status.RECONNECTING) {
            reconnect()
        }

        if (statusState.value == Status.COMPLETED) {
            remoteWriter.send(text)
        }
    }

    /**
     * Send bytes to the server.
     */
    override fun send(bytes: ByteArray) {
        if (statusState.value == Status.RECONNECTING) {
            reconnect()
        }

        if (statusState.value == Status.COMPLETED) {
            remoteWriter.send(bytes)
        }
    }

    /**
     * Send chars to the server.
     */
    override fun send(chars: CharArray) {
        if (statusState.value == Status.RECONNECTING) {
            reconnect()
        }

        if (statusState.value == Status.COMPLETED) {
            remoteWriter.send(chars)
        }
    }

    private fun createReaderAndWriter(isWriterLogging: Boolean, isReaderLogging: Boolean) {
        if (!connection.socket.isClosed and connection.socket.isConnected) {
            remoteReader.createReader(connection.socket, isReaderLogging) {
                connection.reconnect()
            }
            remoteWriter.createWriter(connection.socket, isWriterLogging)
        }
    }

    /**
     * Reconnecting to th server.
     */
    private fun reconnect() {
        remoteReader.destroyReader()
        remoteWriter.destroyWriter()
        _isConnect.value = false

        // When the device connects to the server, it creates Reader and Writer
        coroutineScope.launch(Dispatchers.IO) {
            connection.connect(oldAddress, oldPort, 40000)

            createReaderAndWriter(isWriterLogging, isReaderLogging)
        }
    }
}
