package com.dawn.zgstep.others.tests

import android.os.Looper
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Create by rye
 * at 2021/5/5
 * @description:
 */
fun main(args: Array<String>) {
    val instance = CoroutinesApis()
   //instance.testGlobalScope()
    //instance.errorTest()
     instance.withContextTest()
}

class CoroutinesApis {
    private val mainScope = MainScope()
    fun testGlobalScope() {
        GlobalScope.launch {
            println("执行协程，遇到延迟..")
            delay(1000)
            println("World!")
            println("延迟完毕，执行协程:${Thread.currentThread().name}")
        }
        println("Hello,")
        // Thread.sleep(2000L) //阻塞主线程2s 来保证JVM存活
        runBlocking {
            delay(2000)
        }
    }

    fun testMainScope() {
        mainScope.launch {
            println("是否是主线程:${Looper.getMainLooper() == Looper.myLooper()}")
            launch(Dispatchers.Unconfined) {
                delay(1000L)
                println("GG")
            }
        }
    }

    //Activity或Fragment的onDestroy
    fun onDestroy() {
        mainScope.cancel()
    }

    fun errorTest() {
        GlobalScope.launch {
            testErrorDeal2()
        }
    }

    //捕获异常1
    suspend fun testErrorDeal1() {
        try {
            coroutineScope {
                launch {
                    println("launch和async作用域里的异常捕获不到")
                }
                println("hello...world")
            }
        } catch (e: Exception) {

        }
    }

    //捕获异常2
    suspend fun testErrorDeal2() {
        coroutineScope {
            launch {
                println("全局异常处理")
                throw AssertionError("异常1-1")
                launch(Dispatchers.Main) {
                    throw AssertionError("异常2-2")
                }
            }
        }
    }

    suspend fun testErrorDeal3() {
        //单向1
        coroutineScope {
            launch(CoroutineName("子协程") + SupervisorJob()) {
                println("doSth")
            }
            launch {
                println("doSth2..")
            }
        }
        //单向2
        supervisorScope {
            launch {
                println("test222")
            }
        }
    }

    fun withContextTest() {
        GlobalScope.launch {
          testWithContext()
        }
        runBlocking { //少了这个不行，上面有delay，必须保证jvm没有回收
            delay(2000L)
        }
    }

    suspend fun testWithContext() {
        coroutineScope {
            println("hello ,currentThread:${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                delay(1000L)
                println("withContext Main:${Thread.currentThread().name}")
            }
            withContext(Dispatchers.IO) {
                println("withContext Unconfined:${Thread.currentThread().name}")
            }
        }
    }

}