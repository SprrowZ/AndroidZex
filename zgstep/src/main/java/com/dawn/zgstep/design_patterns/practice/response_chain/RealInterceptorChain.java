package com.dawn.zgstep.design_patterns.practice.response_chain;

import com.dawn.zgstep.design_patterns.practice.extra.Request;
import com.dawn.zgstep.design_patterns.practice.extra.Response;

import java.util.List;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description:
 */
public class RealInterceptorChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;
    private Request request;
    private int mIndex;

    public RealInterceptorChain(List<Interceptor> interceptors, Request request, int index) {
        this.interceptors = interceptors;
        this.request = request;
        this.mIndex = index;
    }


    @Override
    public Response proceed(Request request) {
        //构造新的Chain,一次遍历所有的interceptor
        if (mIndex > interceptors.size()-1) {
            throw new AssertionError();
        }

        Interceptor.Chain next = new RealInterceptorChain(interceptors, request, mIndex + 1);
        Interceptor interceptor = interceptors.get(mIndex);
        Response response = interceptor.intercept(next);
        return response;
    }

    @Override
    public Request request() {
        return request;
    }
}
