package com.dawn.zgstep.others.threads;

import com.dawn.zgstep.others.threads.lockthreads.ThreadLockA;
import com.dawn.zgstep.others.threads.lockthreads.ThreadLockB;
import com.dawn.zgstep.others.threads.lockthreads.ThreadLockC;
import com.dawn.zgstep.others.threads.lockthreads.ThreadLockD;
import com.dawn.zgstep.others.threads.sycthreads.ThreadA;
import com.dawn.zgstep.others.threads.sycthreads.ThreadB;
import com.dawn.zgstep.others.threads.sycthreads.ThreadC;
import com.dawn.zgstep.others.threads.sycthreads.ThreadD;
import com.dawn.zgstep.others.threads.sycthreads.ThreadE;
import com.dawn.zgstep.others.threads.sycthreads.ThreadF;

/**
 * Create by rye
 * at 2021/4/6
 *
 * @description:
 */
public class TestSyn {
    public static void main(String[] args) {
        //testSynThis();
        //  testSynObject();
        // testSynClass();
        //testLock();
        testReadLock();
    }

    /**
     * 测试synchronized(this)
     */
    private static void testSynThis() {
        SynBean bean = new SynBean();
        Thread threadA = new ThreadA(bean);
        threadA.setName("线程A");
        Thread threadB = new ThreadB(bean);
        threadB.setName("线程B");
        threadA.start();
        threadB.start();
    }

    /**
     * 测试synchronized(obj)
     */
    private static void testSynObject() {
        SynBean bean = new SynBean();
        Thread threadC = new ThreadC(bean);
        threadC.setName("线程C");
        Thread threadD = new ThreadD(bean);
        threadD.setName("线程D");
        threadC.start();
        threadD.start();
    }

    private static void testSynClass() {
        SynBean bean1 = new SynBean();
        SynBean bean2 = new SynBean();
        Thread threadE = new ThreadE(bean1);
        threadE.setName("线程E");
        Thread threadF = new ThreadF(bean2);
        threadF.setName("线程F");
        threadE.start();
        threadF.start();
    }

    /**
     * 测试ReentrantLock
     */
    private static void testLock() {
        LockBean bean = new LockBean();
        ThreadLockA lockA = new ThreadLockA(bean);
        ThreadLockB lockB = new ThreadLockB(bean);
        lockA.setName("线程A");
        lockB.setName("线程B");
        lockA.start();
        lockB.start();
    }

    /**
     * 测试ReadLock
     */
    private static void testReadLock() {
        LockBean bean = new LockBean();
        ThreadLockC lockC = new ThreadLockC(bean);
        ThreadLockD lockD = new ThreadLockD(bean);
        lockC.start();
        lockD.start();
    }

}
