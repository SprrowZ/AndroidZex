package com.dawn.zgstep.design_patterns.practice.response_chain2;

import com.dawn.zgstep.design_patterns.practice.extra.Request;
import com.dawn.zgstep.design_patterns.practice.extra.Response;

/**
 * Create by rye
 * at 2020/12/29
 *
 * @description:
 */
public class VenusInterceptor implements Interceptor {
    @Override
    public void intercept(Request request) {
        request.url ="http://www.baidu.com";
        System.out.println("class:"+this.getClass().getSimpleName());
    }
}
