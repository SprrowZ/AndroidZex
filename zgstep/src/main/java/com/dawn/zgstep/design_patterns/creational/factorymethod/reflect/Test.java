package com.dawn.zgstep.design_patterns.creational.factorymethod.reflect;

/**
 * Create by rye
 * at 2021/1/1
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        IRecyclerProduct product = RecyclerFactory.getInstance().createRecycler(RadioRecycler.class);
        System.out.println(product.name);
    }
}
