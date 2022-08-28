package fr.stonks.bot.listeners

import fr.stonks.bot.Bot
import fr.stonks.bot.model.Config
import kotlinx.coroutines.*
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.voice.*
import net.dv8tion.jda.api.hooks.ListenerAdapter

object ChannelListener : ListenerAdapter() {
	
	override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
		if(event.channelJoined.id != Config.curr.channelJoinID)return
		val newChannel = Bot.guild.createVoiceChannel(
			"Channel de ${event.member.effectiveName}",
			Bot.guild.getVoiceChannelById(Config.curr.channelJoinID)!!.parentCategory)
			.complete()
		Bot.guild.moveVoiceMember(event.member, newChannel).queue()
	}
	
	override fun onGuildVoiceMove(event: GuildVoiceMoveEvent) : Unit = runBlocking {
		val left = event.channelLeft
		val new = event.channelJoined
		
		if(new.id == Config.curr.channelJoinID) {
			val newChannel = Bot.guild.createVoiceChannel(
				"Channel de ${event.member.effectiveName}",
				Bot.guild.getVoiceChannelById(Config.curr.channelJoinID)!!.parentCategory)
				.complete()
			Bot.guild.moveVoiceMember(event.member, newChannel).queue()
		}
		if(left.id != Config.curr.channelJoinID) {
			if(left.members.isNotEmpty())return@runBlocking
			
			launch {
				delay(50)
				left.delete().complete()
			}
		}
	}
	
	override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent): Unit = runBlocking {
		val channel = event.channelLeft
		
		if(channel.id == Config.curr.channelJoinID)return@runBlocking
		if(channel.members.isNotEmpty())return@runBlocking
		
		launch {
			delay(50)
			channel.delete().complete()
		}
	}
}