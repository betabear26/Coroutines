import kotlinx.coroutines.*
import kotlin.math.log

fun main() {
    //variousThreads()
    //unconfinedVsConfined()
    jumpingBetweenThreads()
}


fun variousThreads(){
    runBlocking {
        launch {
            println("main : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("New Thread")) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }
    }
}

fun unconfinedVsConfined(){
    runBlocking {
        launch(Dispatchers.Unconfined) {
            println("Unconfined : working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("Unconfined : working in thread ${Thread.currentThread().name}")
        }

        launch {
            println("Main : working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("Main : working in thread ${Thread.currentThread().name}")
        }
    }
}

fun jumpingBetweenThreads(){
    newSingleThreadContext("ctx1").use { ctx1 ->
        newSingleThreadContext("ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                println("Started in ${Thread.currentThread().name}")
                withContext(ctx2){
                    println("Working in ${Thread.currentThread().name}")
                }
                println("Back to ${Thread.currentThread().name}")
            }
        }

    }

}