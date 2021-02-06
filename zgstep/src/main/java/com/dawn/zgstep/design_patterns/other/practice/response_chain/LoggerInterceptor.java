package com.dawn.zgstep.design_patterns.other.practice.response_chain;

import com.dawn.zgstep.design_patterns.other.practice.extra.Response;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description:
 */
public class LoggerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Response response = chain.proceed(chain.request());
        response.logInfo = "log some what....";
        System.out.println("LoggerInterceptor.....");
        return response;
    }
}
