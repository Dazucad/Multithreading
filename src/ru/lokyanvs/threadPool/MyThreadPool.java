package ru.lokyanvs.threadPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
    private ThreadPoolTask[] pool;
    private Queue<Runnable> threadQueue;
    private boolean shutDown;
    private ReentrantLock lock;

    public MyThreadPool(int count) {
        lock = new ReentrantLock();
        pool = new ThreadPoolTask[count];
        threadQueue = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            pool[i] = new ThreadPoolTask(this);
            pool[i].start();
        }
    }

    Runnable getTask() {
        Runnable task;
        lock.lock();
        task = threadQueue.poll();
        lock.unlock();
        return task;
    }

    public void addTask(Runnable task) {
        lock.lock();
        threadQueue.offer(task);
        lock.unlock();

    }

    boolean isShutDown() {
        if (threadQueue.size() == 0) return shutDown;
        return false;
    }

    public void shutDown() {
        shutDown = true;
    }
}

