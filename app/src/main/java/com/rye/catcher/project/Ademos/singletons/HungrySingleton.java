package com.rye.catcher.project.Ademos.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function: 饿汉模式
 */
public class HungrySingleton {
    /**
     * 饿汉特点：实例static，私有构造函数，getInstance方法为static
     */
    private static final HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton() {
        System.out.println("HungrySingleton instance");
    }

    public static HungrySingleton getInstance() {
        return singleton;
    }
}
