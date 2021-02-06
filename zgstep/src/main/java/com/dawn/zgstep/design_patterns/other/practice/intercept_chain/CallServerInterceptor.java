package com.dawn.zgstep.design_patterns.other.practice.intercept_chain;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public class CallServerInterceptor implements Interceptor {
    @Override
    public boolean intercept(Chain chain) {
        boolean result = chain.proceed(chain.request());
        if (result) {
            System.out.println("end..");
            return true;
        }else{
            System.out.println("a.ha..keep moving:"+this.getClass().getSimpleName());
        }
        return false;
    }
}
