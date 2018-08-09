package com.example.myappsecond.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZZG on 2018/8/9.
 */
public class ThreadPoolManager {
    /**
     * 说明：下面这些常量我是根据AsyncTask的源码配置的，大家可以根据自己需求自行配置
     */
    //根据cpu的数量动态的配置核心线程数和最大线程数
    private static final int CPU_COUNT         = Runtime.getRuntime().availableProcessors();
    //核心线程数 = CPU核心数 + 1
    private static final int CORE_POOL_SIZE    = CPU_COUNT + 1;
    //线程池最大线程数 = CPU核心数 * 2 + 1
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    //非核心线程闲置时超时1s
    private static final int KEEP_ALIVE        = 1;


    // 要确保该类只有一个实例对象，避免产生过多对象消费资源，所以采用单例模式
    private  ThreadPoolManager() {
    }

    private static  ThreadPoolManager sInstance;

    public synchronized static  ThreadPoolManager getsInstance() {
        if (sInstance == null) {
            sInstance = new  ThreadPoolManager();
        }
        return sInstance;
    }

    // 线程池的对象
    private ThreadPoolExecutor executor;

    // 使用线程池，线程池中线程的创建完全是由线程池自己来维护的，我们不需要创建任何的线程
    // 我们所需要做的事情就是往这个池子里面丢一个又一个的任务
    public void execute(Runnable r) {
        if (executor == null) {
            /**
             * corePoolSize:核心线程数
             * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
             * keepAliveTime：非核心线程闲置时间超时时长
             * unit：keepAliveTime的单位
             * workQueue：等待队列，存储还未执行的任务
             * threadFactory：线程创建的工厂
             * handler：异常处理机制
             *
             */
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        }

        executor.execute(r);// 把一个任务丢到了线程池中

    }

    public void cancel(Runnable r) {
        if (r != null) {
            executor.getQueue().remove(r);//把任务移除等待队列
        }
    }
}
