package com.example.myappsecond.sdks.beans;

import java.io.Serializable;

/**
 * Created at 2018/9/12.
 *
 * @author Zzg
 */
public class WeatherBean implements Serializable {
    public static final String TEMPERATURE="temperature";
    public static final String WEATHER="weather";
    public static final String WEATHER_RESULT="result";
    public static final String WEATHER_TODAY="today";
    private String resultcode;
    private String reason;
    private Result result;

    private class Result {
//        SK sk;
//        Today today;
    }
}
