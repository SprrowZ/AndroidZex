package com.dawn.zgstep.design_patterns.other.practice.intercept_chain;

import com.dawn.zgstep.design_patterns.other.practice.extra.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        Request request = new Request();
        request.url = "http://www.baidu.com";
        List<Interceptor> interceptors = new ArrayList<>();
        Interceptor cacheInterceptor = new CallServerInterceptor();
        Interceptor callServerInterceptor = new CacheInterceptor();
        Interceptor logInterceptor = new LogInterceptor();

        interceptors.add(cacheInterceptor);
        interceptors.add(callServerInterceptor);
        interceptors.add(logInterceptor);

        Interceptor.Chain chain = new RealInterceptorChain(request,0,interceptors);
        chain.proceed(request);

    }
}
