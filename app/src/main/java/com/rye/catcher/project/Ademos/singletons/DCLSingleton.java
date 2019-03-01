package com.rye.catcher.project.Ademos.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function: DCL双重锁机制（两次检查instance是否为空，提高效率）
 */
public class DCLSingleton {

    //private static DCLSingleton mInstance=null;


    //volatile关键字是可见性，保证我们本地没有mInstance的副本，禁止jvm指令重排序优化，
    // 下面三步就可以按照顺序进行，保证线程安全
    private static volatile DCLSingleton mInstance=null;
    private DCLSingleton(){

    }
    public void doSomething(){
        System.out.println("hello z!");
    }
    public static DCLSingleton getInstance(){
        if (mInstance==null){//节省效率
            synchronized (DCLSingleton.class){
                if (mInstance==null){//可能多个线程同时进入外部的检查，不锁上可能会创建多个实例
                    mInstance=new DCLSingleton();
                    //不是原子操作，jvm先给instance分配内存，第二步调用此类的构造方法
                    //来构造变量，第三步就将instance对象指向我们刚才分配的内存空间
                    //以上三步顺序不确定！！可能导致出错,解决方法给instance加上 volatile关键字
                }
            }
        }
        return mInstance;
    }
}
