package com.dawn.zgstep.design_patterns.creational.factorymethod.reflect;

/**
 * Create by rye
 * at 2021/1/1
 *
 * @description: 反射工厂
 */
public abstract class IRecyclerFactory {

    abstract IRecyclerProduct createRecycler(Class<? extends IRecyclerProduct> clazz);

    public static IRecyclerFactory getInstance(){
        return new RecyclerFactory();
    }
}
