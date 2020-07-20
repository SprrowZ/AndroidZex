package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class HwComputer extends Computer {
    @Override
    void printComputerInfo() {
        System.out.println(className);
    }
}
