import kotlinx.coroutines.*
import okhttp3.internal.wait

fun main() = runBlocking {
	
	val j = launch {
		delay(5000)
		println("a")
	}
	
	println("b")
	
}