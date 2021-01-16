package com.dawn.zgstep.design_patterns.other.practice.response_chain2;

import com.dawn.zgstep.design_patterns.other.practice.extra.Request;

/**
 * Create by rye
 * at 2020/12/29
 *
 * @description:
 */
public interface Interceptor {
    //参数是Request还是Response，取决于你要做什么了
    void intercept(Request request);

    interface Chain {
        void proceed();

        Chain addInterceptor(Interceptor interceptor);

        Request request();
    }
}
