package fr.stonks.bot.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object TicketsTable : IntIdTable("tickets") {
	val channelID = long("channel")
	val sourceID = long("source")
}