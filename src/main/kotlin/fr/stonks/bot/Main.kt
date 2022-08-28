package fr.stonks.bot

import fr.stonks.bot.manager.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

fun main() {
	Bot.connect()
	println("Everything is Ready !")
}