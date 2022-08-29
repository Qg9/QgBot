package fr.stonks.bot.model

import fr.stonks.bot.Bot
import fr.stonks.bot.table.TicketsTable
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Ticket(id: EntityID<Int>) : IntEntity(id) {
	
	companion object : IntEntityClass<Ticket>(TicketsTable)
	
	var channelID by TicketsTable.channelID
	var sourceID by TicketsTable.sourceID
	
	val channel: TextChannel?
		get() = Bot.guild.getTextChannelById(channelID)
	
	val source: Member?
		get() = Bot.guild.getMemberById(sourceID)
	
}