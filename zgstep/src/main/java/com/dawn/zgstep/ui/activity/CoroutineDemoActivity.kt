package com.dawn.zgstep.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dawn.zgstep.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CoroutineDemoActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_demo)
        job = Job()
        loadPicture()

    }

    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun loadPicture() {
        launch {
            val picture = loadByUrl()
            // job.join()
            launch(Dispatchers.Main) {
                refreshUI(picture)
            }
        }
    }

    fun testAsync() {
        launch {
            val intResult = asyncDeferred().await()
        }
    }

    private suspend fun asyncDeferred() = async {
        doSth1()
    }

    private suspend fun doSth1(): Int {
        delay(1000)
        return 13
    }

    //作用域函数1
    private suspend fun testCoroutineScope() = coroutineScope {
        launch {
            println("hello...")
        }
    }
    //作用域函数2
    private suspend fun testSupervisorScope() = supervisorScope {
        launch {
            println("world...")
        }
    }


    private fun loadByUrl() = Unit
    private fun refreshUI(data: Unit) = Unit
}