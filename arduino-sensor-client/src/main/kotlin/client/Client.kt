package client

import kotlinx.coroutines.delay
import org.zeromq.SocketType
import org.zeromq.ZMQ
import org.zeromq.ZMQException

class Client {
    private val context = ZMQ.context(1)

    private var _client: ZMQ.Socket? = null

    private suspend fun getClient(): ZMQ.Socket {
        var client: ZMQ.Socket? = this._client
        while (client == null) {
            try {
                client = context.socket(SocketType.SUB)
                client.connect("tcp://192.168.0.102:5555")
                client.subscribe(byteArrayOf())
            } catch (e: ZMQException) {
                client = null
                println("Can't connect to the server")
                delay(100)
            }
        }
        return client
    }

    suspend fun receiveMessage(): String {
        val client = getClient()
        var message = client.recvStr(ZMQ.NOBLOCK)
        while (message == null) {
            delay(100)
            message = client.recvStr()
        }
        return message
    }
}