package com.dawn.zgstep.demos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多线程操作List，顺序输出List的值
 */
public class MultiThreadListTest {
    public static  final List<Long> list= Collections.synchronizedList(new ArrayList<Long>());

    public static  void main(String[] args){
        for (int i=0;i<1000;i++){
            list.add(Long.valueOf(i));
        }
     Thread thread1=new Thread(new MyThread());
        thread1.setName("Thread--1");
        thread1.start();

     Thread thread2=new Thread(new MyThread());
     thread2.setName("Thread--2");
     thread2.start();

     Thread thread3=new Thread(new MyThread());
     thread3.setName("Thread--3");
     thread3.start();

//     Thread thread4=new Thread(new MyThread());
//     thread4.setName("Thread--4");
//     thread4.start();
    }


}
class MyThread implements Runnable{
 private final  List<Long> list= MultiThreadListTest.list;
    @Override
    public void run() {

           for (int i=0;i<list.size();) {
               synchronized (list) {
                   try {
                       Thread.sleep(50);
                   } catch (Exception e) {

                   }
                   if (list==null||list.size()==0){
                       return ;
                   }
                   System.out.println("Name:" + Thread.currentThread().getName() + "---Value:" + list.get(i));

                    list.remove(i);
               }
           }
    }
}