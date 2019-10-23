package com.dawn.zgstep.demos.pattern.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function:静态内部类
 */
public class StaticInnerSingleton {

    public static StaticInnerSingleton getInstance(){
        return Singleton.INSTANCE;
    }

    /**
     * 不使用这个类的时候，jvm就不会加载这个类，
     * 完成了懒汉加载，也通过static关键字实现了线程安全
     * static保证了内存中的唯一性，synchronized十分影响性能
     */
    private static class Singleton{
        private static final StaticInnerSingleton INSTANCE=
                new StaticInnerSingleton();
    }
}
