package com.dawn.zgstep.design_patterns.structural.proxy.dynamic;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description: 委托类【被代理类】
 */
public class LoginImpl implements ILoginIn, ILogout, ILoginControl {
    @Override
    public void login() {
        System.out.println("login...");
    }

    @Override
    public void logout() {
        System.out.println("logout...");
    }

    @Override
    public void control() {
        System.out.println("control...");
    }

    @Override
    public void control2() {
        System.out.println("control2...");
    }

    @Override
    public void control3() {
        System.out.println("control3...");
    }
}

interface ILoginIn {
    void login();
}

interface ILogout {
    void logout();
}

interface ILoginControl {
    void control();
    void control2();
    void control3();
}