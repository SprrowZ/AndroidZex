package com.dawn.zgstep.design_patterns.principle.interfacesegregation;

/**
 * Create by rye
 * at 2020-07-01
 *
 * @description:
 */
 //   v1
//public class Dog  implements  IAnimalAction{
//    @Override
//    public void eat() {
//
//    }
//
//    @Override
//    public void fly() {
//
//    }
//
//    @Override
//    public void swim() {
//
//    }
//}
    // v2
public class Dog  implements  IEatAnimalAction,ISwimAnimalAction{

    @Override
    public void eat() {

    }

    @Override
    public void swim() {

    }
}