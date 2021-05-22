package com.dawn.zgstep.others.threads.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by rye
 * at 2021/5/10
 *
 * @description:
 */
class Storage {
    public final int MAX_SIZE = 10;
    public List dataSource = new CopyOnWriteArrayList<String>();
    public Object lock = new Object();

    public void test() {
        ExecutorService produceExecutors = Executors.newFixedThreadPool(3);
        ExecutorService consumeExecutors = Executors.newFixedThreadPool(3);

    }
}
