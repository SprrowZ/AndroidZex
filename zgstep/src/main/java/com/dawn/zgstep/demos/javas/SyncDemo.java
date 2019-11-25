package com.dawn.zgstep.demos.javas;

/**
 * Created By zzg
 * at 2019/7/22
 * synchronized 关键字
 */
public class SyncDemo {
    public static void main(String[] args){
     //  lockCodeBlock();
//       myLockBlock();
      //  myLock2();
     //   synMethodTest();
       // syncMethodTest2();
     //   staticSyncTest();
      //  syncClass();
      //  syncVar();
        deadLock();
    }

    /**
     * 自己写的测试类--------同一个实体
     */
    public static void myLockBlock(){
        final MySync mySync=new MySync();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                mySync.youMethod();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                mySync.youMethod();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }

    /**
     * 不同对象
     */
    public static void myLock2(){
        final MySync mySync=new MySync();
        final MySync mySync1=new MySync();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                mySync.youMethod();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                mySync1.youMethod();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }


    /**
     * 同一个对象
     */
    public static void lockCodeBlock(){
        SyncRunnable runnable=new SyncRunnable();
        Thread  thread1=new Thread(runnable,"Thread--111");
        Thread  thread2=new Thread(runnable,"Thread--222");
        thread1.start();
        thread2.start();
    }

    /**
     * 同一个对象的同步方法
     */
    public static void synMethodTest(){
        final MySync2 sync=new MySync2();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.notSyncMethod();
                sync.syncMethod();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.syncMethod();
                sync.notSyncMethod();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }

    /**
     * 多个对象的同步方法
     */
    public static void syncMethodTest2(){
        final MySync2 sync=new MySync2();
        final MySync2 sync2=new MySync2();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.syncMethod();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                sync2.syncMethod();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }

    public static void staticSyncTest(){
        final MySync2 sync=new MySync2();
        final MySync2 sync2=new MySync2();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.stataicSyncMethod();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                sync2.stataicSyncMethod();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }

    /**
     * 类锁
     */
    public static void syncClass(){
        final MySync2 sync=new MySync2();
        final MySync2 sync2=new MySync2();
        final MySync2 sync3=new MySync2();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.syncClass();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                sync2.syncClass();
            }
        },"thread--2");
        Thread thread3=new Thread(new Runnable() {
            @Override
            public void run() {
                sync3.syncClass();
            }
        },"thread--3");
        thread1.start();
        thread2.start();
        thread3.start();
    }

    /**
     * 对象锁
     */
    public static void syncVar(){
        final MySync2 sync=new MySync2();
        final MySync2 sync2=new MySync2();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                sync.syncVar();
            }
        },"thread--1");
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                sync2.syncVar();
            }
        },"thread--2");
        thread1.start();
        thread2.start();
    }


    public static void deadLock(){
        DeadRunnable runnableA=new DeadRunnable("A");
        DeadRunnable runnableB=new DeadRunnable("B");

        runnableA.setDeadRunnable(runnableB);
        runnableB.setDeadRunnable(runnableA);

        Thread thread1=new Thread(runnableA,"Thread--A");
        Thread thread2=new Thread(runnableB,"Thread--B");

        thread1.start();
        thread2.start();

    }


}

/**
 * 测试
 */
class SyncRunnable implements Runnable{
    private static int count;
    public SyncRunnable(){
        count=0;
    }
    @Override
    public void run() {
        synchronized (this){/***********锁代码块***********/
            for (int i=0; i<5;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+ "--" + count++);
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount(){
        return count;
    }
}

class MySync {
    public int count1;
    public int count2;
    public MySync(){
        count1=0;
        count2=0;
    }
    public void youMethod( ){
        synchronized (this){
            for (int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"--Synchronized:"+count1++);
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
//        for (int j=0;j<5;j++){
//            try{
//                Thread.sleep(100);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+"--Non:"+count2++);
//        }
    }
}

class MySync2{
    int count=0;
    int count2=100;
    public static final Object lock=new Object();

    public static int count3=0;

    public synchronized void syncMethod(){
        for (int i=0;i<5;i++){
            try {
                System.out.println(Thread.currentThread().getName()+":"+count++);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void notSyncMethod(){
        for (int i=0;i<5;i++){
            try {
                System.out.println(Thread.currentThread().getName()+"--notSync:"+count2++);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized static  void stataicSyncMethod(){
        for (int i=0;i<5;i++){
            try {
                System.out.println(Thread.currentThread().getName()+":"+count3++);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //类锁
    public void  syncClass(){
        synchronized (MySync2.class){
            for (int i=0;i<5;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+":"+count++);
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    //使用变量当做锁
    public void syncVar(){
        synchronized (lock){
            for (int i=0;i<5;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+":"+count++);
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 自己做个死锁
 */
class DeadRunnable implements Runnable{
    public DeadRunnable deadRunnable;
    public String objName;
     public DeadRunnable(String objName){
         this.objName=objName;
     }
    public void setDeadRunnable(DeadRunnable dead){
        this.deadRunnable =dead;
    }
    public synchronized void methodA(){
       String name=Thread.currentThread().getName();
       System.out.printf("线程%s:进入 methodA\n",name);
       try{
           Thread.sleep(1000);
       }catch (Exception e){

       }
       System.out.printf("线程%s:尝试进入methodB\n",name);
       deadRunnable.methodB();
       System.out.printf("线程%s:离开methodB\n",name);
    }

    public synchronized  void methodB(){
        String name=Thread.currentThread().getName();
        System.out.printf("线程%s:进入 methodB\n",name);
        try{
            Thread.sleep(100);
        }catch (Exception e){

        }
        System.out.printf("线程%s:尝试进入methodA\n",name);
        deadRunnable.methodA();
        System.out.printf("线程%s:离开methodA",name);
    }

    @Override
    public void run() {
            if ("A".equals(objName)){
                methodA();
            }else{
                methodB();
            }
    }
}