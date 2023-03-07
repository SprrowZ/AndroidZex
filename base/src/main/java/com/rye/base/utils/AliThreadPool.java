package com.rye.base.utils;

import android.os.Debug;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;

import com.rye.base.BuildConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/8/9 8:35 下午
 */
public class AliThreadPool {
    private static final String TAG = "AliThreadPool";
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MAIN_QUEUE_POOL_SIZE = 30;
    private static final int HIGH_PRIORITY_QUEUE_POOL_SIZE = 10;
    private ThreadPoolExecutor mainThreadPool;
    private ThreadPoolExecutor highPriorityThreadPool;
    private ThreadPoolExecutor singleTaskThreadPool;
    private boolean running;
    private int highPoolSize = poolSize(0.5D);
    private int ioPoolSize = ioIntensivePoolSize();
    private static final ThreadFactory mainPriorityThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread thread = new AliThreadPool.LowPriorityThread(r, "LowPriorityAsyncTask #" + this.mCount.getAndIncrement());
            thread.setPriority(1);
            return thread;
        }
    };
    private static final ThreadFactory singleTaskPriorityThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread thread = new AliThreadPool.LowPriorityThread(r, "singleTask_NormalPriorityAsyncTask #" + this.mCount.getAndIncrement());
            thread.setPriority(5);
            return thread;
        }
    };
    private static final ThreadFactory highPriorityThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "HighPriorityAsyncTask #" + this.mCount.getAndIncrement());
        }
    };
    private static AliThreadPool instance = new AliThreadPool();
    private static AtomicInteger taskCount = new AtomicInteger(0);

    public static AliThreadPool instance() {
        return instance;
    }

    private AliThreadPool() {
        this.init(CPU_COUNT, this.ioPoolSize, CPU_COUNT, this.highPoolSize);
    }

    private void init(int mainCoreSize, int mainMaxSize, int highCoreSize, int highMaxSize) {
        this.mainThreadPool = new ThreadPoolExecutor(mainCoreSize, mainMaxSize, 3L, TimeUnit.SECONDS, new LinkedBlockingQueue(30), mainPriorityThreadFactory, new AliThreadPool.AbortPolicy(false));
        this.mainThreadPool.allowCoreThreadTimeOut(true);
        this.highPriorityThreadPool = new ThreadPoolExecutor(highCoreSize, highMaxSize, 3L, TimeUnit.SECONDS, new ArrayBlockingQueue(10), highPriorityThreadFactory, new AliThreadPool.AbortPolicy(true));
        this.highPriorityThreadPool.allowCoreThreadTimeOut(true);
        this.singleTaskThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue(), singleTaskPriorityThreadFactory, new AliThreadPool.AbortPolicy(false));
        this.running = true;
    }

    public void runInSingleThreadPool(Runnable task) {
        if (!this.running) {
            Log.e("AliThreadPool", "addTask failed! thread pool running is false!");
        } else if (task != null) {
            this.executeTask(this.singleTaskThreadPool, task);
        }
    }

    public static void runNow(Runnable runnable) {
        instance().runTaskNow(runnable);
    }

    public static <T> Future<T> runNow(Callable<T> callable) {
        return instance().runTaskNow(callable);
    }

    public static void runInBackground(Runnable runnable) {
        instance().runTaskInBackground(runnable);
    }

    public static <T> Future<T> runInBackground(Callable<T> callable) {
        return instance().runTaskInBackground(callable);
    }

    public void runTaskNow(Runnable runnable) {
        this.addTask(runnable, true);
    }

    public <T> Future<T> runTaskNow(Callable<T> callable) {
        return this.addTask(callable, true);
    }

    public void runTaskInBackground(Runnable runnable) {
        this.addTask(runnable, false);
    }

    public <T> Future<T> runTaskInBackground(Callable<T> callable) {
        return this.addTask(callable, false);
    }

    private void addTask(Runnable task, boolean isHighPriority) {
        if (!this.running) {
            Log.e("AliThreadPool", "addTask failed! thread pool running is false!");
        } else if (task != null) {
            this.executeTask(isHighPriority ? this.highPriorityThreadPool : this.mainThreadPool, task);
        }
    }

    private <T> Future<T> addTask(Callable<T> callable, boolean isHighPriority) {
        if (!this.running) {
            Log.e("AliThreadPool", "addTask failed! thread pool running is false!");
            return null;
        } else {
            return callable == null ? null : this.submitTask(isHighPriority ? this.highPriorityThreadPool : this.mainThreadPool, callable);
        }
    }

    public void resume() {
        this.init(CPU_COUNT, this.ioPoolSize, CPU_COUNT, this.highPoolSize);
    }

    public void destory() {
        this.running = false;
        this.highPriorityThreadPool.shutdown();
        this.mainThreadPool.shutdown();
        this.singleTaskThreadPool.shutdown();
        this.highPriorityThreadPool = null;
        this.mainThreadPool = null;
        this.singleTaskThreadPool = null;
    }

    private void executeTask(ThreadPoolExecutor executor, Runnable task) {
        if (BuildConfig.DEBUG) {
            taskCount.incrementAndGet();
        }

        if (task instanceof AliThreadPool.TaskWrap) {
            executor.execute(task);
        } else {
            executor.execute(new AliThreadPool.TaskWrap(task));
        }

    }

    private <T> Future<T> submitTask(ThreadPoolExecutor executor, Callable<T> callable) {
        if (BuildConfig.DEBUG) {
            taskCount.incrementAndGet();
        }

        return executor.submit(callable);
    }

    public static int ioIntensivePoolSize() {
        double blockingCoefficient = 0.8D;
        return poolSize(blockingCoefficient);
    }

    public static int poolSize(double blockingCoefficient) {
        return (int)((double)CPU_COUNT / (1.0D - blockingCoefficient));
    }

    private static class TaskWrap implements Runnable {
        private int taskId;
        private Runnable realTask;
        public int rejectCount;

        private TaskWrap(Runnable realTask) {
            this.taskId = AliThreadPool.taskCount.get();
            this.realTask = realTask;
        }

        public void run() {
            try {
                this.realTask.run();
            } catch (Exception var2) {
                if (BuildConfig.DEBUG) {
                   // ToastHelper.showToast("AliThreadPool TaskWrap catch exception");
                    throw new RuntimeException(var2);
                }

                Log.e("AliThreadPool", "TaskWrap.run! throw exception!", var2);
            }

        }
    }

    private static class AbortPolicy implements RejectedExecutionHandler {
        private boolean retry;

        public AbortPolicy(boolean retry) {
            this.retry = retry;
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (this.retry && r instanceof AliThreadPool.TaskWrap) {
                AliThreadPool.TaskWrap taskWrap = (AliThreadPool.TaskWrap)r;
                AliThreadPool.runInBackground(taskWrap.realTask);
            }

        }
    }

    private static class LowPriorityThread extends Thread {
        private LowPriorityThread(Runnable r, String name) {
            super(r, name);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }
}
