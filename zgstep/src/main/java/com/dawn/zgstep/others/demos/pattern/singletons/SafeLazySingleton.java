package com.dawn.zgstep.others.demos.pattern.singletons;

/**
 * Created at 2019/2/28.
 *
 * @author Zzg
 * @function:
 */
public class SafeLazySingleton {
    private static SafeLazySingleton instance;
    private SafeLazySingleton(){
    }

    public static void main(String[] args){

    }

    /**
     * 主要用synchronized同步方法
     * @return
     */
    public static synchronized  SafeLazySingleton getInstance(){
        if (instance==null){
            instance=new SafeLazySingleton();
        }
        return instance;
    }

    /**
     * 同步代码块
     * @return
     */
    public static SafeLazySingleton getInstance2(){
        synchronized (SafeLazySingleton.class){
            if (instance==null){
                instance=new SafeLazySingleton();
            }
        }
        return instance;
    }
}
