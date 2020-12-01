package com.rye.base.impl

import com.google.gson.Gson
import com.rye.base.network.Error
import com.rye.base.network.ResponseErrorBody
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.lang.Exception

/**
 *Created By RyeCatcher
 * at 2019/8/13
 */
abstract class HttpObserver<T>:Observer<T> {
    private lateinit var disposable:Disposable
    abstract  fun  onStart()
    abstract  fun  onSuccess(data: T?)
    abstract  fun  onError(code:Int,msg:String)
    abstract  fun  onFinish()

    override fun onSubscribe(d: Disposable) {
        this.disposable=d
        onStart()
    }

    override fun onNext(tResult: T) {
        onFinish()//网络请求只有一次，next直接结束了
        onSuccess(tResult)
    }

    override fun onError(throwable: Throwable) {
             onFinish()
        val msg=throwable.message
        try{
           if (isHttpError(msg.toString())){
               onError(Error.HTTP_ERROR,"NO INTERNET CONNECTION")
           }else if(throwable is HttpException){
             val exception=throwable
               if (!isCanHandle(exception)){
                   val body=exception.response().errorBody()?.string()
                    val responseErrorBody=Gson().fromJson(body,ResponseErrorBody::class.java)
                    onError(Error.SERVER_ERROR,responseErrorBody.message)
               }
           }else if (msg!!.contains("java.lang.IllegalStateException")){
               onError(Error.APP_ERROR,"Program Exception")
           }else if (msg.contains("body is null")){
               onSuccess(null)
           }else{
               onError(Error.UNKNOW,msg)
           }
        }catch (e:Exception){
                onError(Error.APP_ERROR, msg.toString())
        }
        disposable?.dispose()
    }

    /**
     * 可以预处理的错误
     */
    private fun isCanHandle(exception: HttpException):Boolean{
       when(exception.code()){
           401->{
               //token 失效，发广播重登陆
               return true
           }
           403->{
              return true
           }
           500->{
              return true
           }
       }
        return false
    }

    override fun onComplete() {
         disposable?.dispose()
    }

    /**
     * 链路错误
     */
    private fun isHttpError(msg:String):Boolean{
        val errorArray= arrayOf("timeout",
                "java.net.ConnectException",
                "java.net.SocketTimeoutException",
                "failed",
                "Failed to connect to",
                "stream was reset",
                "Unable to resolve host",
                "SSL handshake time out ",
                "time out ")
         for (error in errorArray.withIndex()){
             if (msg.contains(error.value)){
                 return true
             }
         }
        return false
    }


}