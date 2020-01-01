package com.dawn.zgstep.demos.pattern.proxy_pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理模式---动态代理
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;
    public DynamicProxy(Object object){
        this.object=object;
    }

    /**
     * 通过invoke方法来调动具体的被代理的方法
     * @param o
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        beforeDefend();
        Object result=method.invoke(object,args);
        afterDefend();
        return result;
    }

    private void beforeDefend(){
        System.out.println("我是原告律师，以下是我方辩词");
    }

    private void afterDefend(){
        System.out.println("辩护完毕");
    }
}
