package com.dawn.zgstep.others.demos.pattern.adpterpattern;

/**
 * Created at 2019/3/8.
 *
 * @author Zzg
 * @function:类适配器
 */
public class Adapter extends Adaptee implements Target {


    @Override
    public void sampleOpertion2() {
        System.out.println("sampleOpertion2...");
    }
}