package com.dawn.zgstep.design_patterns.other.practice.response_chain;

import com.dawn.zgstep.design_patterns.other.practice.extra.Response;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description:  拦截器的最后一个，获取真正的Response
 */
public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Response response = new Response();
        response.code  = 200;
        System.out.println("CallServerInterceptor.....");
        return response;
    }
}
