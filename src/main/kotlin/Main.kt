import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>){
    //simpleRun()
    /*runBlocking {
        scopedRun()
    }*/

    //explicitJob()

    lightWeight()
}

private fun simpleRun() {
    runBlocking {
        launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
    }
}

private suspend fun scopedRun() = coroutineScope {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}

private fun explicitJob(){
    runBlocking {
        val job = launch {
            delay(1000L)
            println("World!!")
        }

        println("Hello")
        job.join()
        println("Done")
    }
}

private fun lightWeight(){
    runBlocking {
        repeat(100_000){
            launch {
                delay(500L)
                print(".")
            }
        }
    }
}