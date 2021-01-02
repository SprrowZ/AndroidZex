package com.dawn.zgstep.design_patterns.practice.response_chain;

import com.dawn.zgstep.design_patterns.practice.extra.Request;
import com.dawn.zgstep.design_patterns.practice.extra.Response;

/**
 * Create by rye
 * at 2020-11-05
 *
 * @description: 仿照OkHttp责任链
 */
public interface Interceptor {
    Response intercept(Chain chain);

    interface Chain {//request、response都交给Chain处理，interceptor就是chain上的一个子节点
        Response proceed(Request request);
        Request request();
    }
}
