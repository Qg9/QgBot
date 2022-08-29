package fr.stonks.bot.listeners

import fr.stonks.bot.manager.TicketManager
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission.ADMINISTRATOR
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.awt.Color

object TicketListener : ListenerAdapter() {
	
	override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
		
		val member = event.member
		
		if(!event.isGuildCommand)return
		if(event.name != "ticket")return
		if(!member!!.hasPermission(ADMINISTRATOR))return
		
		TicketManager.sendTicketMessage(event)
	}
	
	override fun onButtonInteraction(event: ButtonInteractionEvent) {
		if(event.button.id == "ticket") {
			if(TicketManager.hasTicket(event.member!!)) {
				event.reply("Tu as déjà un ticket").setEphemeral(true).queue()
				return
			}
			event.replyModal(TicketManager.request()).queue()
		}
		if(event.button.id == "delete-ticket") TicketManager.deleteTicket(event.channel.asTextChannel())
	}
	
	override fun onModalInteraction(event: ModalInteractionEvent) {
		if (event.modalId != "ticket")return
		val modal = event.interaction
		val channel = TicketManager.create(event.member!!,
			modal.getValue("desc")!!.asString,
			modal.getValue("budget")?.asString ?: "non défini",
			modal.getValue("deadline")?.asString ?: "non défini"
		)
		event.reply("Tu as ouvert un ticket ${channel.asMention} !").setEphemeral(true).queue()
	}
}