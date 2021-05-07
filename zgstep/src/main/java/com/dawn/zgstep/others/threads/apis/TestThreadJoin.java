package com.dawn.zgstep.others.threads.apis;

/**
 * Create by rye
 * at 2021/5/7
 *
 * @description:
 */
class TestThreadJoin {
    public static void main(String[] args) {
       testJoin();
    }

    public static void testJoin() {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("线程开始---睡眠也开始了");
                Thread.sleep(2000);
                System.out.println("睡眠结束");
            } catch (Exception e) {

            }
        });
        thread.start();
        System.out.println("正常运行代码...");
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这句话应该是在线程运行结束后才开始运行哦~");
    }

}
