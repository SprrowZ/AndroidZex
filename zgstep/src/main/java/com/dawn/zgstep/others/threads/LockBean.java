package com.dawn.zgstep.others.threads;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by rye
 * at 2021/4/7
 *
 * @description: 测试Lock:ReentrantLock
 */
public class LockBean {
    private final Lock mReentrantLock = new ReentrantLock();
    //读写锁
    private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();
    private Lock readLock = reentrantReadWriteLock.readLock();

    private int mSharedVar = 99;

    /**
     * 测试ReentrantLock
     */
    public void method1() {
        sout("method1 start...");
        mReentrantLock.lock();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
        sout("method1 end...");

    }

    /**
     * 测试ReentrantLock
     */
    public void method2() {
        sout("method2 start...");
        if (mReentrantLock.tryLock()) {
            try {
                sout("method2 getLock..");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mReentrantLock.unlock();
                sout("method2 end...");
            }
        } else {
            sout("method2 not getLock..");
        }

    }

    /**
     * 测试readLock
     */
    public void method3() {
        readLock.lock();
        try {
            sout("method3 mSharedVar:" + mSharedVar);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 测试readLock
     */
    public void method4() {
        if (readLock.tryLock()) {
            readLock.lock();
            try {
                sout("method4 mSharedVar:" + mSharedVar);
            } finally {
                readLock.unlock();
            }
        }

    }


    /**
     * 测试writeLock
     */
    public void method5() {
        writeLock.lock();
        try {
            mSharedVar += 1;
            sout("method5 mSharedVar:" + mSharedVar);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 测试writeLock
     */
    public void method6() {
        try {
            if (writeLock.tryLock(4000,TimeUnit.MILLISECONDS)) {
                writeLock.lock();
                try {
                    sout("method6 mSharedVar:" + mSharedVar);
                } finally {
                    writeLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private void sout(String content) {
        System.out.println(getTime() + "  " + content);
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(date);
    }
}
