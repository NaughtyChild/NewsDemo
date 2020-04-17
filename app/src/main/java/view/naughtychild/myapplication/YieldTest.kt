package view.naughtychild.myapplication

val fibonacci = sequence<Int> {
    this.yield(1)
    var cur = 1
    var next = 1
    while (true) {
        yield(next)
        val temp = cur + next
        cur = next
        next = temp
    }
}

fun main() {
    fibonacci.take(20).iterator().forEach {
        println(it)
    }
}