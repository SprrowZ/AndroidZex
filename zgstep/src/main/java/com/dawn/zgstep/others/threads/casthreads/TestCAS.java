package com.dawn.zgstep.others.threads.casthreads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by rye
 * at 2021/4/9
 *
 * @description:
 */
public class TestCAS {
    //volatile不保证原子性
    private volatile int mCount = 0;
    private AtomicInteger mAtomicCount = new AtomicInteger(0);

    public static void main(String[] args) {
          TestCAS testCAS = new TestCAS();
          testCAS.testIncrease();
    }
    public void testIncrease(){
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                   for (int i = 0;i<10000;i++){
                        increase();
                      // atomicIncrease();
                   }
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         System.out.println(mCount);
       // System.out.println(mAtomicCount);
    }
    private void increase(){
        mCount++;
    }
    private void atomicIncrease(){
        mAtomicCount.incrementAndGet();
    }

}
