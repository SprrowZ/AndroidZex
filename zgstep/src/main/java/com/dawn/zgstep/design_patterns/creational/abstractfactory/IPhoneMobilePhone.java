package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class IPhoneMobilePhone extends MobilePhone {
    @Override
    void sysInfo() {
        System.out.println(className);
    }
}
