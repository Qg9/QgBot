package fr.stonks.bot.manager

import fr.stonks.bot.model.Config
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json.Default
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object ConfigManager {
	
	private val file = File("./config.json")
	
	val config: Config
	
	private val json: Json = Json {
		prettyPrint = true
		encodeDefaults = true
		explicitNulls = true
	}
	
	init {
		
		if(!file.exists()) {
			file.createNewFile()
			config = Config()
			
			val out = file.outputStream()
			json.encodeToStream(config, out)
			out.flush()
			out.close()
			
		}else{
			val inp = file.inputStream()
			config = json.decodeFromStream(inp)
			inp.close()
		}
	}
}