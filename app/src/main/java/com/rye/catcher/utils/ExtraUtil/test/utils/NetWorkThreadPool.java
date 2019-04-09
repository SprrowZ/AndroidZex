package com.rye.catcher.utils.ExtraUtil.test.utils;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池,防止子线程创建过多导致问题
 */
public class NetWorkThreadPool {
    private static final String TAG="NetWorkThreadPool";
    public static final int MULT = 5;
    public static final int SINGLE = 1;
    private static final int THREAD_POOL_SIZE=Runtime.getRuntime().availableProcessors()*2+1;
    private ExecutorService threadPool;

    public NetWorkThreadPool getInstance(){
        return singletonHolder.mutiInstance;
    }

    private NetWorkThreadPool(int type) {

        if (type == MULT)
            threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        else
            threadPool = Executors.newSingleThreadExecutor();

    }

    /**
     * singleTonHolder
     */
    private static class singletonHolder {
        private static final NetWorkThreadPool mutiInstance = new NetWorkThreadPool(MULT);
        private static final NetWorkThreadPool singleInstance = new NetWorkThreadPool(SINGLE);
    }
    public void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

//    /**
//     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//     * @param callback
//     */
//    public void getCachedThreadPool(BaseCallback callback){
//        threadPool=Executors.newCachedThreadPool();
//        Runnable runnable= () -> {
//         callback.start();
//            Log.i(TAG, "activeThread-cached: "+Thread.activeCount());
//        };
//        threadPool.submit(runnable);
//    }
//
//    /**
//     *创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//     * @param callback
//     */
//    public void getFixedThreadPool(BaseCallback callback){
//        threadPool= Executors.newFixedThreadPool(10);
//        Runnable runnable= () -> {
//            callback.start();
//            Log.i(TAG, "activeThread-fixed:"+Thread.activeCount());
//        };
//        threadPool.submit(runnable);
//    }
//
//    /**
//     * 创建一个定长线程池，支持定时及周期性任务执行。
//     * @param callback
//     */
//    public void getScheduledThreadPool(BaseCallback callback){
//        ExecutorService scheduledThreadPool=Executors.newScheduledThreadPool(10);
//        Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//            callback.start();
//            }
//        };
//        ((ScheduledExecutorService) scheduledThreadPool).schedule(runnable,3,TimeUnit.SECONDS);
//    }
//
//    /**
//     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
//     * @param callback
//     */
//   public void getSingleThreadPool(BaseCallback callback){
//        ExecutorService singleThreadPool=Executors.newSingleThreadExecutor();
//        Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                callback.start();
//                Log.i(TAG, "activeThread-single:"+Thread.activeCount());
//            }
//        };
//        singleThreadPool.execute(runnable);
//
//   }
//
//    private interface  BaseCallback{
//        void start();
//    }

}
