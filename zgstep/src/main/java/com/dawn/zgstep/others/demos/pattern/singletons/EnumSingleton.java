package com.dawn.zgstep.others.demos.pattern.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function:枚举类型单例
 */
public enum EnumSingleton {
    INSTANCE;
    public void doSomething(){
        System.out.println("ok..");
    }
}
