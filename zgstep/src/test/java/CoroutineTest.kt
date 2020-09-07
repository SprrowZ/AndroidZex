import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Create by rye
 * at 2020-08-17
 * @description: 测试协程
 */
class CoroutineTest {
    @Test
    fun coroutineTest1() {
        runBlocking {
            val job = GlobalScope.launch {
                delay(1000L)
                println("World!")
            }
            println("Hello,")
            job.join()
            println("over..")
        }
    }

    @Test
    fun cancelCoroutine() {
        runBlocking {
            var count = 0
            val job = GlobalScope.launch {
                repeat(1000) {
                    println("I'm trying repeat...${count++}")
                    delay(500)
                }
            }

            delay(1300)
            job.cancel()
            job.join()
            println("I'm Over...")
        }
    }

    @Test
    fun checkCancel() {
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) {
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("Hello ${i++}")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1000L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    @Test
    fun checkTimeOut() {
        runBlocking {
            val result = withTimeoutOrNull(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i")
                    delay(500L)
                }
                "Done"
            }
            println("result is $result")
        }
    }

    @Test
    fun checkAsyncOne() {
        runBlocking {
            val time = measureTimeMillis {
                val one = doSomethingOne()
                val two = doSomethingTwo()
                one + two
            }
            println("总共耗时：$time")
        }
    }

    @Test
    fun checkAsyncTwo() {
        runBlocking {
            val time = measureTimeMillis {
                val one = async { doSomethingOne() }
                val two = async { doSomethingTwo() }
                one.await() + two.await()
            }
            println("总共耗时：$time")
        }
    }

    @Test
    fun checkAsyncThree() {
        runBlocking {
            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) { doSomethingOne() }
                val two = async(start = CoroutineStart.LAZY) { doSomethingTwo() }
                two.start()
                one.start()
                println("结果：${one.await() + two.await()}")
            }
            println("总耗时：$time")
        }
    }

    @Test
    fun checkAsyncFour() {
        runBlocking {
            val time = measureTimeMillis {
                concurrentSum()
            }
            println("总耗时：$time")
        }
    }

    private suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingOne() }
        val two = async { doSomethingTwo() }
        one.await() + two.await()
    }


    private suspend fun doSomethingOne(): Int {
        println("doSomethingOne....")
        delay(1000L) // do sth
        return 13
    }

    private suspend fun doSomethingTwo(): Int {
        println("doSomethingTwo...")
        delay(1000L) // do sth
        return 14
    }


}