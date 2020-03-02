package view.naughtychild.myapplication

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    var job = launch {
        repeat(100) { i ->
            println("挂起中$i")
            delay(1000)
        }
    }
    var job2 = async {
        delay(500L)
        return@async "hello"
    }
    println(job2.await())
    delay(1300)
    println("主线程等待中")
    job.cancel()
    job.join()
    println("主线程即将退出")
}