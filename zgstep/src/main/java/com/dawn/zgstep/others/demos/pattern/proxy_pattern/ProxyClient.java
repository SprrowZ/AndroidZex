package com.dawn.zgstep.others.demos.pattern.proxy_pattern;

import java.lang.reflect.Proxy;

public class ProxyClient {
    public static void  main(String[] args){

        IDefender accuser=new Accuser();

        accuser.defend();

        DynamicProxy proxy= new DynamicProxy(accuser);
        ClassLoader loader=accuser.getClass().getClassLoader();
        IDefender proxyIDfender=(IDefender) Proxy.newProxyInstance(loader,new Class[]{IDefender.class},proxy);
        proxyIDfender.defend();
    }
}
