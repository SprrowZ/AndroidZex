package com.dawn.zgstep.design_patterns.practice.interceptors;

import android.util.Log;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description:
 */
public class BridgeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Response response = chain.proceed(chain.request());
        response.message = "bridge";
        System.out.println("BridgeInterceptor.....");
        return response;
    }
}
