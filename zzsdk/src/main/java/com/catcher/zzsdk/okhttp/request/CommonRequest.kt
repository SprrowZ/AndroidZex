package com.catcher.zzsdk.okhttp.request

import android.util.Log
import okhttp3.FormBody
import okhttp3.Request
import java.lang.StringBuilder

/**
 *@author admin
 *@function 接收请求参数，为我们生成Request对象
 */
class CommonRequest {
 companion object{
     /**
      * 创建Post的Request对象
      */
     fun createPostRequest(url:String,params:RequestParams): () -> Request {
         val mFormBodyBuild=FormBody.Builder()
         params.urlParams.forEach{
             //将请求参数遍历放入请求体中
             mFormBodyBuild.add(it.key,it.value)
         }
        val mFormBody=mFormBodyBuild.build()
        return fun():Request{
          return Request.Builder()
                  .url(url)
                  .post(mFormBody)
                  .build()
        }

     }

     fun createGetRequest(url:String, params: RequestParams?): Request {
         val urlBuilder=StringBuilder(url).append("?")
             params?.urlParams?.forEach{
                 urlBuilder.append(it.key).append("=")
                         .append(it.value).append("&")
             }


               return Request.Builder()
                       .url(urlBuilder.substring(0,urlBuilder.length-1))//去掉最后一个&.get()
                       .get()
                       .build()

     }

 }
}