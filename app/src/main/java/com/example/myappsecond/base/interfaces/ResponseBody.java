package com.example.myappsecond.base.interfaces;

/**
 * Created by ZZG on 2018/8/23.
 */
public class ResponseBody {
 PostsInfo postsInfo;
 String profile;
    @Override
    public String toString() {
        return "ResponseBody{" +
                "postsInfo=" + postsInfo +
                ", profile='" + profile + '\'' +
                '}';
    }
}
