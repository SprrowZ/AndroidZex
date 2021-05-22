package com.dawn.zgstep.others.threads.producer;

/**
 * Create by rye
 * at 2021/5/10
 *
 * @description:
 */
class Producer implements Runnable {
    private Storage mStorage;

    public Producer(Storage storage) {
        this.mStorage = storage;
    }

    @Override
    public void run() {
        synchronized (mStorage.lock) {
            while (mStorage.dataSource.size() + 1 > mStorage.MAX_SIZE) {//队列已满
                System.out.println("队列已满！");
                try {
                    mStorage.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mStorage.dataSource.add(1);
            mStorage.lock.notifyAll();
            System.out.println("当前缓冲队列大小：" + mStorage.dataSource.size());
        }
    }
}

class Consumer implements Runnable {
    private Storage mStorage;

    public Consumer(Storage storage) {
        this.mStorage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mStorage.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // mStorage.dataSource.remove();

    }
}
