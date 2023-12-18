package storage.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import storage.dbo.EventDbo
import storage.dbo.EventTable
import java.time.OffsetDateTime

class EventDao(private val database: Database) {
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }

    suspend fun insertEvent(dateTime: OffsetDateTime, type: String, value: String?) = dbQuery {
        EventTable.insert {
            it[EventTable.dateTime] = dateTime
            it[EventTable.type] = type
            it[EventTable.value] = value
        }
    }

    suspend fun getEvents() = dbQuery {
        EventTable.selectAll().map {
            EventDbo(
                id = it[EventTable.id],
                dateTime = it[EventTable.dateTime],
                type = it[EventTable.type],
                value = it[EventTable.value],
            )
        }
    }
}