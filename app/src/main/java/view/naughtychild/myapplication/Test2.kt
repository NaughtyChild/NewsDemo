package view.naughtychild.myapplication

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main() {
    runBlocking {
        val token = getToken()
        val response = getResponse()
        setText()
        println("token=$token,response=$response")
        GlobalScope.launch {  }
    }
    println("sdsdsd")
}

suspend fun getToken(): String {
    delay(2000)
    println("getToken")
    return "ask"
}

suspend fun getResponse(): String {
    delay(2000)
    println("gerResponse")
    return "name"
}

suspend fun setText() {
    delay(2000)
    println("setText")
}



