package com.dawn.zgstep.design_patterns.other.practice.response_chain2;

import com.dawn.zgstep.design_patterns.other.practice.extra.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020/12/29
 *
 * @description: 处理责任链
 */
public class RealInterceptorChain implements Interceptor.Chain {
    private List<Interceptor> interceptors = new ArrayList<>();

    @Override
    public void proceed() {
        for (Interceptor interceptor : interceptors) {
            interceptor.intercept(request());
        }
    }

    @Override
    public Request request() {
        Request request = new Request();
        return request;
    }

    @Override
    public Interceptor.Chain addInterceptor(Interceptor interceptor) {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor);
        }
        return this;
    }

}
