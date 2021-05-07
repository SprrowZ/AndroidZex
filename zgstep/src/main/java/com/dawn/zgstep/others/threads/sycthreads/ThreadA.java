package com.dawn.zgstep.others.threads.sycthreads;

import com.dawn.zgstep.others.threads.SynBean;

/**
 * Create by rye
 * at 2021/4/6
 *
 * @description: 测试synchronized(this)
 */
public class ThreadA extends  Thread{
    private SynBean synBean;
    public ThreadA(SynBean bean){
        this.synBean =bean;
    }
    @Override
    public void run() {
        synBean.methodA();
    }
}
