package com.dawn.zgstep.others.threads.sycthreads;

import com.dawn.zgstep.others.threads.SynBean;

/**
 * Create by rye
 * at 2021/4/6
 *
 * @description:
 */
public class ThreadF extends Thread {
    private SynBean synBean;
    public ThreadF(SynBean bean){
        this.synBean = bean;
    }
    @Override
    public void run() {
        super.run();
        synBean.methodF();
    }
}
