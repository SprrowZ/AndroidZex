package com.dawn.zgstep.design_patterns.practice.intercept_chain;

import android.util.Log;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public boolean intercept(Chain chain) {
        boolean result = chain.proceed(chain.request());
        if (result) {
            System.out.println("end..");
            return true;
        } else {
            System.out.println("wow..keep moving:" + this.getClass().getSimpleName());
        }
        return true;
    }
}
