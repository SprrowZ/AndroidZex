package com.dawn.zgstep.tests;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by rye
 * at 2021/4/29
 *
 * @description:
 */
class TestAqs {
    ReentrantLock lock = new ReentrantLock();
    CountDownLatch latch = new CountDownLatch(3);
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    ExecutorService cachedThreads = Executors.newCachedThreadPool();

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    ExecutorService newFixedThreadPool = new ThreadPoolExecutor(10,
            10,
            15,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(10));


    public void test() {
        TestRunnable testThread = new TestRunnable();
        singleThreadPool.submit(testThread);
       // singleThreadPool.shutdown();
        singleThreadPool.shutdownNow();
        singleThreadPool.execute(new TestRunnable());
    }

    public void testMaxValueThread() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            singleThreadPool.submit(new TestRunnable());
        }
    }

    public void testFuture(){
        TestCallable callable = new TestCallable();
        ExecutorService threadPools = Executors.newFixedThreadPool(1);
        Future submit = threadPools.submit(callable);
        submit.cancel(true);
    }
    public void testFutureTask() throws ExecutionException, InterruptedException {
        //FutureTask

        FutureTask task = new FutureTask(()->"111");
        Thread thread = new Thread(task);
        thread.start();
        Object result = task.get();
        System.out.println(result);
    }

    public static void main(String[] args) {
        TestAqs testDemo = new TestAqs();
       // testDemo.testMaxValueThread();
        try {
            testDemo.testFutureGet();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testDemo.test();
    }
    public void testFutureGet() throws ExecutionException, InterruptedException {
        Person person = new Person("original name");
        Task task = new Task(person);
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        Future<Person> future = threadPool.submit(task, person);
        Person result = future.get();
        System.out.println(result.name);
    }
}
class Person{
    String name;
    Person(String name){
        this.name = name;
    }
}

class Task implements Runnable {
    Person mPerson;
    Task(Person person){
        this.mPerson = person;
    }
    @Override
    public void run() {
        this.mPerson.name = "modified name...";
    }
}

class TestRunnable implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (Throwable e) {

        }
        System.out.println("emm..do sth?");

    }
}
class TestCallable implements Callable{

    @Override
    public Object call() throws Exception {

        return null;
    }
}

class TestFuture implements Future{

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return null;
    }
}