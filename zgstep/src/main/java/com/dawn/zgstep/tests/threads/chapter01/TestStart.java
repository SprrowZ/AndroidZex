package com.dawn.zgstep.tests.threads.chapter01;

/**
 * Create by rye
 * at 2021/3/23
 *
 * @description:
 */
public class TestStart {

    public static void main(String[] args) {
        testStart();
    }

    private static void testStart(){
        Thread thread1 = new Thread();
        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();
    }
}


class ThreadAction extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("thread run ....");
    }
}