package com.rye.catcher.demos.kotlins

import android.util.Log
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.*



/**
 *Created by 18041at 2019/6/24
 *Function:
 */
class TContinuation {
    //-----------------------创建协程------------------------
    val suspendLambda= suspend{
        "hewllo"
    }
    val completion=object: Continuation<String> {
        override fun resumeWith(result: Result<String>) {

        }
        override val context: CoroutineContext
            get()  = EmptyCoroutineContext



    }
    val coroutine:Continuation<Unit> = suspendLambda.createCoroutine(completion)
    //开启协程，也可以通过suspend的startCoroutine(Continuation<T>)方法来启动协程
    fun start(){
        coroutine.resume(Unit)
    }
    //
    fun launchTest(){

    }

}

fun main(args: Array<String>) {
//   runBlocking {
//       fetchData()
//   }
//    GlobalScope.launch(Dispatchers.Default) {
//        System.out.println(Thread.currentThread().name)
//        fetchData()
//    }
    testDiapatchers()
}
suspend fun fetchData():String{
    delay(2000)
    System.out.println(Thread.currentThread().name)
    return "what a f!"
}
fun testDiapatchers(){
    GlobalScope.launch(Dispatchers.IO) {
        //调度器指定在IO线程中运行
        System.out.println(Thread.currentThread().name)
        val today= Date(System.currentTimeMillis())
        val date= SimpleDateFormat("yyyy-MM-dd").format(today)
        delay(2000)
        withContext(Dispatchers.Main){
            System.out.println(Thread.currentThread().name+"---time:$date")
        }
    }
}












suspend  fun test()= coroutineScope{
    launch {
        delay(1000)
        println("Kotlin Coroutines World!")
    }
    println("Hello")
}


