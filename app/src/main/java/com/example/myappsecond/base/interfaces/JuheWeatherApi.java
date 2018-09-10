package com.example.myappsecond.base.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ZZG on 2018/9/10.
 */
public interface JuheWeatherApi {
    @GET("/")
    Call<ResponseBody> getWeather(@Query("format") String format,
                                  @Query("cityname") String cityname,
                                  @Query("key") String key);
}
