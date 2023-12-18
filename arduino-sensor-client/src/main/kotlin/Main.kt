import client.Client
import kotlinx.coroutines.*
import storage.Storage
import java.time.OffsetDateTime

fun main(): Unit = runBlocking(SupervisorJob()) {
    launch {
        val client = Client()
        val storage = Storage()
        while (true) {
            val message = client.receiveMessage()
            val messageParts = message.split(" ")

            val type = if (messageParts.isNotEmpty()) messageParts[0] else null
            val value = if (messageParts.size > 1) messageParts[1] else null

            storage.insertEvent(
                dateTime = OffsetDateTime.now(),
                type = type.orEmpty(),
                value = value
            )

            println(message)
        }
    }
}