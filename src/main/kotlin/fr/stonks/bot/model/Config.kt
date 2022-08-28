package fr.stonks.bot.model

import fr.stonks.bot.manager.ConfigManager

@kotlinx.serialization.Serializable
data class Config(
	val token: String = "Token ICI",
	val channelInviteID: String = "1010139398563708978",
	val ticketCategoryID: String = "1010139398563708978",
	val roleID: String = "1013184968870068345",
	val channelJoinID: String = "908414295832932396"
) {
	
	companion object {
		val curr get() = ConfigManager.config
	}
}