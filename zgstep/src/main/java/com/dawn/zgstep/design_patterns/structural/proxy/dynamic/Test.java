package com.dawn.zgstep.design_patterns.structural.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public class Test {

    public static void main(String[] args) {
        dynamicProxy();
    }

    private static void dynamicProxy() {
        LoginImpl loginImpl = new LoginImpl();
        ILoginControl loginControl = (ILoginControl) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                loginImpl.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before..."+proxy.getClass());
                        Object result = method.invoke(loginImpl, args);
                        System.out.println("after...");
                        return result;
                    }
                });
        loginControl.control();
        loginControl.control2();
        loginControl.control3();
    }
}
