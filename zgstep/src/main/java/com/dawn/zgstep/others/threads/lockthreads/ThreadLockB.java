package com.dawn.zgstep.others.threads.lockthreads;

import com.dawn.zgstep.others.threads.LockBean;

/**
 * Create by rye
 * at 2021/4/7
 *
 * @description:
 */
public class ThreadLockB extends Thread {
    private LockBean lockBean;

    public ThreadLockB(LockBean bean) {
        this.lockBean = bean;
    }

    @Override
    public void run() {
        super.run();
        lockBean.method2();
    }
}
