package com.rye.catcher.project.Ademos.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function: 懒汉模式
 */
public class LazySingleton {
    private static  LazySingleton instance;
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if (instance==null){
            instance=new LazySingleton();
        }
        return  instance;
    }
    public  static void createString(){
        System.out.println("create String...");
    }
}
