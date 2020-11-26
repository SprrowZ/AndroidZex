package com.dawn.zgstep.design_patterns.practice.interceptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description:
 */
public class Test {

    public static void main(String[] args) {

        List<Interceptor> interceptors = new ArrayList<>();
        BridgeInterceptor bridgeInterceptor = new BridgeInterceptor();
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        LoggerInterceptor loggerInterceptor = new LoggerInterceptor();
        CallServerInterceptor callServerInterceptor = new CallServerInterceptor();

        interceptors.add(loggerInterceptor);
        interceptors.add(bridgeInterceptor);
        interceptors.add(cacheInterceptor);
        //这个必须放最后一位，获取真正的Response,但实际上是第一个执行的Interceptor,上面几个拦截器通过proceed方法，
        //层层调用拦截器上的每一个元素，所以interceptors的执行顺序和添加顺序相反
        interceptors.add(callServerInterceptor);
        Request request = new Request();
        request.url ="http://www.baidu.com";
         //责任链启动
        Interceptor.Chain chain = new RealInterceptorChain(interceptors,request,0);
        chain.proceed(request);
    }
}
