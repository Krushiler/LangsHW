package storage.dbo

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import java.time.OffsetDateTime

data class EventDbo(
    val id: Int,
    val dateTime: OffsetDateTime,
    val type: String,
    val value: String?,
)

object EventTable : Table() {
    val id = integer("id").autoIncrement()
    val dateTime = timestampWithTimeZone("dateTime")
    val type = varchar("type", 255)
    val value = varchar("value", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}