package com.dawn.zgstep.design_patterns.creational.simplefactory;

/**
 * Create by rye
 * at 2020-07-02
 *
 * @description:
 */
public class AdultVideo extends Video {
    @Override
    void produce() {
        System.out.println("PythonVideo");
    }
}