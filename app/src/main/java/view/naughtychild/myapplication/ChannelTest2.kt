package view.naughtychild.myapplication

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

@ExperimentalCoroutinesApi
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun main() = runBlocking {
    /* val squares = produceSquares()
     squares.consumeEach { println(it) }
     println("Done!")*/
    val producer = put()
    get(producer).join()
    producer.cancel()
}

fun CoroutineScope.put(): ReceiveChannel<Int> = produce(capacity = 5) {
    var i = 0
    while (true) {
        send(++i)
        println("发送$i")
        delay(500)
    }
}


fun CoroutineScope.get(channel: ReceiveChannel<Int>) = launch {
    while (true) {
        var i = channel.receive()
        println("接收$i")
        delay(500)
    }
}
