package com.dawn.zgstep.others.threads.sycthreads;

import com.dawn.zgstep.others.threads.SynBean;

/**
 * Create by rye
 * at 2021/4/6
 *
 * @description: 测试synchronized(obj)
 */
public class ThreadD extends Thread{
    private SynBean synBean;

    public ThreadD(SynBean bean) {
        this.synBean = bean;
    }

    @Override
    public void run() {
        synBean.methodD();
    }
}
