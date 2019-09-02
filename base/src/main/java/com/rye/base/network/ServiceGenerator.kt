package com.rye.base.network

import com.rye.base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

class ServiceGenerator {
    companion object {
        private const val TIME_OUT = 5L
        private var retrofit:Retrofit? =null
        private var okHttpClient: OkHttpClient? = null
        private var serviceMap:MutableMap<String,Any>? =null
        private var logging:HttpLoggingInterceptor?= null
        private val httpClientBuilder:OkHttpClient.Builder
        init {
            httpClientBuilder=OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .addInterceptor(DefaultHeaderAddInterceptor())
               if (BuildConfig.DEBUG){//重写一下
                   logging=HttpLoggingInterceptor()
                   logging!!.level = HttpLoggingInterceptor.Level.BODY
                   httpClientBuilder.addInterceptor(logging)
               }
            okHttpClient= httpClientBuilder.build()
            serviceMap= mutableMapOf()
        }

       fun <T> getService(serviceClz:Class<T>): T? {
         if (retrofit==null|| !Gateway.getBaseUrl().equals(retrofit?.baseUrl())){
             retrofit=Retrofit.Builder()
                     .baseUrl(Gateway.getBaseUrl())
                     .client(okHttpClient)
                     .addConverterFactory(GsonConverterFactory.create())
                     .addConverterFactory(EmptyConverterFactory())
                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                     .build()
             serviceMap?.clear()
         }
           return if (serviceMap!!.containsKey(serviceClz.name)){
               serviceMap!![serviceClz.name] as T?
           }else{
               var instance=retrofit?.create(serviceClz)
               if (instance != null) {
                   serviceMap!!.put(serviceClz.name, instance)
               }
               return instance
           }
       }

    }
}