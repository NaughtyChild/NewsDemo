package view.naughtychild.myapplication

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val channel = Channel<Int>()
    runBlocking {
        launch {
            get(channel)
        }
        launch {
            put(channel)
        }
    }
}

suspend fun get(channel: Channel<Int>) {
    while (true) {
        println(channel.receive())
        delay(200)
    }
}

suspend fun put(channel: Channel<Int>) {
    var i = 0
    while (true) {
        channel.send(i++)
        delay(200)
    }
}