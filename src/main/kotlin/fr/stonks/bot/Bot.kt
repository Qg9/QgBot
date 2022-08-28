package fr.stonks.bot

import fr.stonks.bot.listeners.*
import fr.stonks.bot.model.Config
import kotlinx.coroutines.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy

object Bot {
	
	lateinit var jda: JDA
	lateinit var guild: Guild
	
	fun connect() {
		
		jda = JDABuilder.createDefault(Config.curr.token, GatewayIntent.values().asList())
			.setMemberCachePolicy(MemberCachePolicy.ALL)
			.addEventListeners(JoinQuitListener, TicketListener, ChannelListener)
			.build()
		
		jda.upsertCommand("ticket", "send ticket message").queue()
			
		jda.awaitReady()
			
		guild = jda.guilds[0]
	}
}