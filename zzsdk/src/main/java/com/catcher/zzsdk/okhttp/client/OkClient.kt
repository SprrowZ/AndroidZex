package com.catcher.zzsdk.okhttp.client

import com.catcher.zzsdk.okhttp.others.HttpsUtils
import com.catcher.zzsdk.okhttp.request.GetBuilder
import com.catcher.zzsdk.okhttp.response.NormalCallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 *@author admin
 *@function
 */

private const val TIME_OUT= 30.toLong()//超时参数

var mOkHttpClient:OkHttpClient?=null
class OkClient {

    //url
    private var url:String ?=null
    //method
    private var method:String?=null
    constructor(url:String,method: String){
       this.url=url
       this.method=method

    }


    companion object{

        init {//静态代码块
            val okHttpBuilder=OkHttpClient.Builder()
            okHttpBuilder.connectTimeout(TIME_OUT,TimeUnit.SECONDS)
            okHttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS)
            okHttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS)
            //允许重定向
            okHttpBuilder.followRedirects(true)
            //支持https
            okHttpBuilder.hostnameVerifier { hostname, session -> true }
            okHttpBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager())
            //生成client对象
            mOkHttpClient =okHttpBuilder.build()
        }
        fun getClient(): OkHttpClient? {
            return mOkHttpClient
        }
        /**
         * 自定义Okhttp的JSon数据回调
         */
        fun sendRequest(request:Request,commCallback: NormalCallback): Call? {
            val call= mOkHttpClient?.newCall(request)
            call?.enqueue(commCallback)
            return call
        }

        /**
         * 发送具体的http请求
         */
        fun sendRequest(request:Request,commCallback: Callback): Call? {
            val call= mOkHttpClient?.newCall(request)
            call?.enqueue(commCallback)
            return call
        }

        /**
         * get请求----建造者模式，一层层深入
         */
        fun get():GetBuilder{
            return GetBuilder()
        }


    }
}