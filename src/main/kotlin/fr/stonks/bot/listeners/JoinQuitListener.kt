package fr.stonks.bot.listeners

import fr.stonks.bot.Bot
import fr.stonks.bot.model.Config
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color


object JoinQuitListener : ListenerAdapter() {
	
	override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
		val user = event.user

		if(user.isBot)return
		
		val channel = Bot.guild.getTextChannelById(Config.curr.channelInviteID)
		val embed = EmbedBuilder().setTitle("Le QG")
			.addField("Bienvenue ${user.asMention} !", "", false)
			.setColor(Color(47, 49, 54))
			.setThumbnail(user.avatarUrl)
			.build()
		
		Bot.guild.addRoleToMember(user, Bot.guild.getRoleById(Config.curr.roleID)!!).queue()
		channel!!.sendMessageEmbeds(embed).queue()
	}
}