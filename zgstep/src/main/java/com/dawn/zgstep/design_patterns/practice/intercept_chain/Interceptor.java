package com.dawn.zgstep.design_patterns.practice.intercept_chain;

import com.dawn.zgstep.design_patterns.practice.extra.Request;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public interface Interceptor {
    boolean intercept(Chain chain);

    interface Chain {
        Request request();

        Boolean proceed(Request request);
    }
}
