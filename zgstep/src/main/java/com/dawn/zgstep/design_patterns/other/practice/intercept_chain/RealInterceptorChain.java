package com.dawn.zgstep.design_patterns.other.practice.intercept_chain;

import com.dawn.zgstep.design_patterns.other.practice.extra.Request;

import java.util.List;

/**
 * Create by rye
 * at 2020/12/28
 *
 * @description:
 */
public class RealInterceptorChain implements Interceptor.Chain {
    private Request mRequest;
    private int mIndex;
    private List<Interceptor> mInterceptors;

    public RealInterceptorChain(Request request, int index, List<Interceptor> interceptors) {
        this.mRequest = request;
        this.mIndex = index;
        this.mInterceptors = interceptors;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public Boolean proceed(Request request) {
        Interceptor.Chain chain;
        if (mIndex < 0 || mIndex >= mInterceptors.size() - 1) { //当前只有此条件是判断为true的
            chain = null;
        } else {
            chain = new RealInterceptorChain(request, mIndex + 1, mInterceptors);
        }
        Interceptor interceptor = mInterceptors.get(mIndex);
        boolean result = interceptor.intercept(chain);
        return result;
    }
}
