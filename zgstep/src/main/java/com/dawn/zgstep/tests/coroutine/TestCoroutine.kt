package com.dawn.zgstep.tests.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Create by rye
 * at 2020-08-17
 * @description: 创建协程、
 */
class TestCoroutine {

    /**创建协程 方式一 runBlocking*/
    fun coroutineByBlock() {
        runBlocking {
            println("阻塞协程....")
        }
    }

    /**创建协程 方式二 GlobalScope.launch*/
    fun coroutineByGlobalScope() {
        GlobalScope.launch {
            println("GlobalScope.launch创建协程...")
        }
    }

    /**创建协程 方式三 CoroutineContext*/
    fun coroutineByCoroutineContext(context: CoroutineContext) {
        val coroutineScope = CoroutineScope(context)
        coroutineScope.launch {
            println("CoroutineContext创建协程...")
        }
    }

    fun switchThread(context: CoroutineContext) {
        val coroutineScope = CoroutineScope(context)
        coroutineScope.launch(Dispatchers.Main) {
            println("主线程创建...${Thread.currentThread().name}")
        }
    }

    fun coroutineJoin() {
        println("-----")




    }
    fun ccc(){
        println("-----")
    }


}

fun main(args: Array<String>) {
    val testCoroutine = TestCoroutine()
    testCoroutine.ccc()
}