package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public abstract class Computer {
    String className = this.getClass().getSimpleName();
    abstract  void printComputerInfo();
}
