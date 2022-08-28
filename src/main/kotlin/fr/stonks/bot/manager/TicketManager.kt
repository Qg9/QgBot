package fr.stonks.bot.manager

import fr.stonks.bot.Bot
import fr.stonks.bot.model.Config
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.Modal
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle.*
import java.awt.Color

object TicketManager {
	
	val category = Bot.guild.getCategoryById(Config.curr.ticketCategoryID)
	
	fun request(): Modal {
		val input = TextInput.create("desc", "Explique nous ta demande précisement", PARAGRAPH)
			.setRequiredRange(1, 1024)
			.setPlaceholder("Je souhaite un plugin en 1.18 d'item avec...")
			.build()
		
		val budget = TextInput.create("budget", "Donne nous ton budget", SHORT)
			.setRequiredRange(1, 128)
			.setRequired(false)
			.setPlaceholder("Je viens pour estimer mon cahier des charges")
			.build()
		
		val deadline = TextInput.create("deadline", "Informe nous à propos de la Deadline", SHORT)
			.setRequiredRange(1, 128)
			.setRequired(false)
			.setPlaceholder("Je n'ai pas de deadline définis")
			.build()
		
		return Modal.create("ticket", "Ouvrir un ticket")
			.addActionRows(ActionRow.of(input), ActionRow.of(budget), ActionRow.of(deadline))
			.build()
	}
	
	fun create(member: Member, desc: String, budget: String, deadline: String) : TextChannel {
		val channel = Bot.guild.createTextChannel(member.effectiveName, category).complete()
		channel.upsertPermissionOverride(member).grant(Permission.VIEW_CHANNEL).queue();
		channel.sendMessageEmbeds(
			EmbedBuilder()
				.setThumbnail(member.effectiveAvatarUrl)
				.setTitle("Ticket de ${member.effectiveName}")
				.setColor(Color(47, 49, 54))
				.appendDescription("**Description :** $desc \n")
				.appendDescription("**Budget :** ${budget.ifEmpty { "à définir" }} \n")
				.appendDescription("**Deadline :** ${deadline.ifEmpty { "à définir" }} \n")
				.build()
		).addActionRow(Button.danger("delete", "Fermer le ticket")).queue()
		channel.sendMessage(member.asMention).complete().delete().queue()
		return channel
	}
	
	fun hasTicket(member: Member?) : Boolean {
		return !category!!.textChannels.none { member!!.effectiveName.equals(it.name, true) }
	}
}