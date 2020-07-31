package com.dawn.zgstep.design_patterns.behavioral.observer;

public class MainPage implements  LoginObserver {
    @Override
    public void loginSuccess(String personalInfo) {
        System.out.println("MainPage收到用户登录信息："+personalInfo);
    }
}
