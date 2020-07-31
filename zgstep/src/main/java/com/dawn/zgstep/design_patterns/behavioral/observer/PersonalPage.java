package com.dawn.zgstep.design_patterns.behavioral.observer;

public class PersonalPage implements LoginObserver {
    @Override
    public void loginSuccess(String personalInfo) {
        System.out.println("PersonalPager收到登陆信息："+personalInfo);
    }
}
