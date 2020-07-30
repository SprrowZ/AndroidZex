package com.dawn.zgstep.design_patterns.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
       dynamicProxy();
    }
    //静态代理
    private static  void staticProxy(){
        PlayController mPlay = new PlayController();
        PlayDelegate mDelegate = new PlayDelegate(mPlay);
        mDelegate.speedPlay();
    }
    //动态代理
    private static void dynamicProxy(){
        //获取目标对象
        PlayExpand target = new PlayController();
        //获取代理对象
        PlayExpand proxy = (PlayExpand) new ProxyFactory(target).getProxyInstance();
        proxy.speedPlay();
    }
}
