package storage

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import storage.dao.EventDao
import storage.dbo.EventTable
import java.time.OffsetDateTime

class Storage {
    private val database = createDatabase()
    private val dao = EventDao(database)

    private fun createDatabase(): Database {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcURL = "jdbc:sqlite:data/data.db"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(EventTable)
        }
        return database
    }

    suspend fun insertEvent(dateTime: OffsetDateTime, type: String, value: String?) = dao.insertEvent(
        dateTime = dateTime,
        type = type,
        value = value
    )

    suspend fun getEvents() = dao.getEvents()
}