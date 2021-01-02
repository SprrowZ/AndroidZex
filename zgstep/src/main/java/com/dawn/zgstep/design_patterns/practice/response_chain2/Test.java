package com.dawn.zgstep.design_patterns.practice.response_chain2;

/**
 * Create by rye
 * at 2020/12/29
 *
 * @description: 不需要对结果进行处理，单纯的一套处理流程
 */
public class Test {
    public static void main(String[] args) {

        Interceptor.Chain chain = new RealInterceptorChain()
                .addInterceptor(new MercuryInterceptor())
                .addInterceptor(new EarthInterceptor())
                .addInterceptor(new VenusInterceptor());
        chain.proceed();
    }
}
