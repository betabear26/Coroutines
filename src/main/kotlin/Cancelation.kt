import kotlinx.coroutines.*

fun main(){
    //simpleCancel()
    //nonCooperativeCancellation()
    //cooperativeCancellation()
    //closingWithFinally()
    //nonCancellableBlock()
    //timeOut()
    //timeoutWithNull()
    asyncTimeOut()
}

fun simpleCancel(){

    runBlocking {
        val job = launch {
            repeat(1000) {
                println("Sleeping $it")
                delay(500L)
            }
        }

        delay(1197L)
        println("Tired")
        job.cancelAndJoin()
        println("Quitting")
    }
}

fun nonCooperativeCancellation(){
   runBlocking {
       val startTime = System.currentTimeMillis()
       val job = launch(Dispatchers.Default) {
           var nextPrintTime = startTime
           var i = 0
           while (i < 5){
               // yield() // Yield makes it cooperative as it checks for active before proceeding
               if(System.currentTimeMillis() >= nextPrintTime){
                   println("I'm sleep ${i++}")
                   nextPrintTime += 500L
               }
           }
       }

       delay(1300L)
       println("Tired")
       job.cancelAndJoin()
       println("Quitting")
   }
}

fun cooperativeCancellation(){
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {
                // yield() // Yield makes it cooperative as it checks for active before proceeding
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleep ${i++}")
                    nextPrintTime += 500L
                }
            }
        }

        delay(1300L)
        println("Tired")
        job.cancelAndJoin()
        println("Quitting")
    }
}

fun closingWithFinally(){
    runBlocking {
        val job = launch {
            try {
                repeat(1000){
                    println("Sleeping $it")
                    delay(500L)
                }
            } finally {
                println("Finally")
            }
        }

        delay(1300)
        println("Tired of waiting")
        job.cancelAndJoin()
        println("I quit")
    }
}

fun nonCancellableBlock(){
    runBlocking {
        val job = launch {
            try {
                repeat(1000){
                    println("Sleeping $it")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable){
                    println("Running finaly with non cancellable")
                    delay(1000L)
                    println("Can delay for 1 second cuz non cancelable")
                }
            }
        }

        delay(1300)
        println("Tired of waiting")
        job.cancelAndJoin()
        println("I quit")
    }
}

fun timeOut(){
    runBlocking {
        withTimeout(1300L){
            repeat(1000){
                println("Sleeping $it")
                delay(400L)
            }
        }
    }
}

fun timeoutWithNull(){
    runBlocking {
        val result = withTimeoutOrNull(1300L){
            repeat(1000){
                println("Sleeping $it")
                delay(500L)
            }
            "Done"
        }

        println(result)
    }
}

var acquired = 0

class Resource{
    init {
        acquired++
    }

    fun close() { acquired-- }
}

fun asyncTimeOut(){
    runBlocking {
        repeat(100_000){
            launch {
                val resource = withTimeout(60){
                    delay(50)
                    Resource()
                }
                resource.close()
            }
        }
    }

    //May not print 0 everytime
    println(acquired)
}

//The previous can be modified to use finally. In that case all the acquired resources are closed
fun asyncTimeOutFinally(){
    runBlocking {
        repeat(100_000){
            launch {
                var resource: Resource? = null
                try {
                    withTimeout(60){
                        delay(50)
                        resource = Resource()
                    }
                } finally {
                    resource?.close()
                }
            }
        }
    }

    //Will always print 0
    println(acquired)
}