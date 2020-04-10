package com.dawn.zgstep.others.bolts

import android.app.Activity
import android.os.Bundle
import android.telecom.Call
import bolts.CancellationTokenSource
import bolts.Continuation
import bolts.Task
import bolts.TaskCompletionSource
import com.dawn.zgstep.R
import java.lang.Exception
import java.util.concurrent.Callable

class BoltsMainKotlin :Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bolts_main)
    }


    /**
     * 在Ui线程中执行任务
     */
    fun methodOne() {
        Task.call(object:Callable<String>{
            override fun call(): String {
                return ""
            }
        },Task.UI_THREAD_EXECUTOR)
      }

    /**
     * 可取消的线程
     */
    fun methodTwo() {
        val source = CancellationTokenSource()
        Task.call(object :Callable<Any>{
           override fun call(): Any {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       },Task.UI_THREAD_EXECUTOR,source.token)
        //doSomething..
        source.cancel()
    }

    /**
     * callInBackground和call一样，只不过默认执行器为BACKGROUND_EXECUTOR
     */
    fun methodThree() {
        Task.callInBackground(object:Callable<String>{
            override fun call(): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    /**
     * delay延迟任务，一般和ContinueExecute配合使用
     */
    fun methodFour() {
        Task.delay(3000).continueWith(object :Continuation<Void,Any>{
            override fun then(task: Task<Void>?): Any {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    /**
     * continueWith---级联调用，task串串，类似RxJava的flatMap
     */
    fun methodFive() {
       Task.call(object:Callable<String>{
           override fun call(): String {
               return "123"
           }
       },Task.UI_THREAD_EXECUTOR).continueWith(object :Continuation<String,Int> {
           override fun then(task: Task<String>?): Int {
               if (task?.isFaulted == true || task?.isCancelled == true)  return -1
               return task?.result?.toInt() ?: -1
           }
       },Task.UI_THREAD_EXECUTOR)
    }

    /**
     * TaskCompletionSource---可以控制Task的行为
     */
    fun  methodSix() {
       val task = TaskCompletionSource<String>()
       if (!task.task.isCompleted){
           task.trySetCancelled()
           task.trySetResult("完犊子了？")
           task.trySetError(Exception("完蛋TvT"))
       }
    }

    /**
     * continueWithTask--
     */
    fun  methodSeven() {
       Task.call(object:Callable<String>{
           override fun call(): String {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

       }).continueWithTask(object : Continuation<String ,Task<String>> {
           override fun then(task: Task<String>?): Task<String> {
               return  Task.call(object :Callable<String>{
                   override fun call(): String {
                       return "---"
                   }
               })
           }

       },Task.UI_THREAD_EXECUTOR).continueWithTask{
            return@continueWithTask
       }
    }

    fun  methodEight() {

    }

}