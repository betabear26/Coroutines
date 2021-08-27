import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main(){
    //sequentialByDefault()
    //concurrentUsingAsync()
    //lazeStartWithAsync()
    //asyncStyleFunctions()
    cancellationPropagation()
}

suspend fun usefulOne(): Int{
    delay(1000L)
    return 12
}

suspend fun usefulTwo(): Int{
    delay(1200L)
    return 34
}

fun sequentialByDefault(){
    runBlocking {
        val time = measureTimeMillis {
            val one = usefulOne()
            val two = usefulTwo()

            println("The answer is ${one + two}")
        }

        println("Time taken is $time")
    }
}

fun concurrentUsingAsync(){
    runBlocking {
        val time = measureTimeMillis {
            val one = async { usefulOne() }
            val two = async { usefulTwo() }

            println("The answer is ${one.await() + two.await()}")
        }

        println("Time taken is $time")
    }
}

fun lazeStartWithAsync(){
    runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { usefulOne() }
            val two = async(start = CoroutineStart.LAZY) { usefulTwo() }
            //do something
            one.start()
            two.start()

            println("The answer is ${one.await() + two.await()}")
        }

        println("Time taken is $time")
    }
}

fun usefulOneAsync() = GlobalScope.async {
    usefulOne()
}

fun usefulTwoAsync() = GlobalScope.async {
    usefulTwo()
}

fun asyncStyleFunctions(){
    runBlocking {
        val time = measureTimeMillis {
            val one = usefulOneAsync()
            val two = usefulTwoAsync()

            println("The answer is ${one.await() + two.await()}")
        }

        println("Time taken is $time")
    }
}

fun cancellationPropagation(){
    runBlocking {
        try {
            failedSum()
        } catch (e: ArithmeticException) {
            println("Computation failed with ${e.message}")
        }
    }
}

suspend fun failedSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}