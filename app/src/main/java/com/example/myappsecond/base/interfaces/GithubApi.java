package com.example.myappsecond.base.interfaces;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by ZZG on 2018/8/23.
 */
public interface GithubApi {
    /**
     * GET请求
     * @return
     */
    //访问文件地址：https://raw.githubusercontent.com/SprrowZ(用户名)/AndroidZex(仓库名)/master(分支)/.gitignore(路径)
    @GET("/")//不传参数
    Call<ResponseBody> getMessages();
    @GET("users/SprrowZ")//这个参数是用来拼路径的，https://api.github.com/users/{user}，就是这里的{user}
    Call<ResponseBody> getMessages2();
    @GET("repos/{owner}/{repo}")//路径为：https://api.github.com/repos/{owner}/{repo},{owner}/{repo}
    Call<ResponseBody> getMessages3(@Path("owner") String owner,@Path("repo") String repo);//替换路径
    @GET("users/{user}/repos")//路径：https://api.github.com/users/{user}/repos{?type,page,per_page,sort}
    Call<ResponseBody> getMessages4(@Path("user") String user,
                                    @Query("page") int page,@Query("per_page")int per_page);
    @GET("users/{user}/repos")
    Call<ResponseBody> getMessages5(@Path("user") String user, @QueryMap HashMap<String,Integer> info);




}
