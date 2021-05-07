package com.dawn.zgstep.others.threads.lockthreads;

import com.dawn.zgstep.others.threads.LockBean;

/**
 * Create by rye
 * at 2021/4/7
 *
 * @description: 测试读写锁；
 */
public class ThreadLockC extends Thread {
    private LockBean lockBean;
    public ThreadLockC(LockBean bean){
        this.lockBean =bean;
    }

    @Override
    public void run() {
        super.run();
       // lockBean.method3(); //读锁
        lockBean.method5();
    }
}
