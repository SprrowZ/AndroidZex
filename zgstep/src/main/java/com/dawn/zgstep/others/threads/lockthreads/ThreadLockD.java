package com.dawn.zgstep.others.threads.lockthreads;

import com.dawn.zgstep.others.threads.LockBean;

/**
 * Create by rye
 * at 2021/4/7
 *
 * @description:
 */
public class ThreadLockD extends Thread {
    private LockBean lockBean;

    public ThreadLockD(LockBean bean) {
        this.lockBean = bean;
    }

    @Override
    public void run() {
        super.run();
        // lockBean.method4();//读锁
        lockBean.method6();
    }
}
