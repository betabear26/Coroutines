import kotlinx.coroutines.*

fun main(args: Array<String>){
    //simpleCancel()
    nonCooperativeCancellation()
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