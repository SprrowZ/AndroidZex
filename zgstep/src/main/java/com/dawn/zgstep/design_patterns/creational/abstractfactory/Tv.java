package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public  abstract class Tv {
    String className = this.getClass().getSimpleName();
    abstract void printlTvInfo();
}
