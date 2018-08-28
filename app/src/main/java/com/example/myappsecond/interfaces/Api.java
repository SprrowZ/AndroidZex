package com.example.myappsecond.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ZZG on 2018/8/23.
 */
public interface InfoService {
    @GET("/")//不传参数
    Call<ResponseBody> getMessages();
    @GET("RyeCatcher")//这个参数是用来拼路径的，https://api.github.com/users/{user}，就是这里的{user}
    Call<ResponseBody> getMessages2();
    @GET("")
    Call<ResponseBody> getMessage3();
}
