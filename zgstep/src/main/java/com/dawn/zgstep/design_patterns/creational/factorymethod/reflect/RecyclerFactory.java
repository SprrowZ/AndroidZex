package com.dawn.zgstep.design_patterns.creational.factorymethod.reflect;

/**
 * Create by rye
 * at 2021/1/1
 *
 * @description:  通过反射来构造具体的实例，有点是只需要一个工厂！
 */
public class RecyclerFactory extends IRecyclerFactory {
    @Override
    IRecyclerProduct createRecycler(Class<? extends IRecyclerProduct> clazz) {
        try {
            Class instanceClass = Class.forName(clazz.getName());
            return (IRecyclerProduct) instanceClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
