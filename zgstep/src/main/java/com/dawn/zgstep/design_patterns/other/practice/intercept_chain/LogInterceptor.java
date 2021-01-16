package com.dawn.zgstep.design_patterns.other.practice.intercept_chain;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public class LogInterceptor implements Interceptor {

    @Override
    public boolean intercept(Chain chain) {
        if (chain == null) { //说明是第一个
            System.out.println("emm...keep moving:" + this.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
