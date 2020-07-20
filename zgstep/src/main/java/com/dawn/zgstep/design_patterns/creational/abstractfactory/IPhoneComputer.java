package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class IPhoneComputer extends Computer {
    @Override
    void printComputerInfo() {
        System.out.println(className);
    }
}
