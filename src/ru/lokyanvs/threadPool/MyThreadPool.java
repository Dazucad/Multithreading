package ru.lokyanvs.threadPool;

import java.util.LinkedList;
import java.util.Queue;

public class MyThreadPool {
    private ThreadPoolTask[] pool;
    private Queue<Runnable> threadQueue;
    private final Thread taskExecutor;
    private Queue<Integer> availablePoolElement;
    private boolean shutDown;

    public MyThreadPool(int count) {
        pool = new ThreadPoolTask[count];
        for (int i = 0; i < count; i++) {
            pool[i] = new ThreadPoolTask(i, this);
            pool[i].start();
        }
        threadQueue = new LinkedList<>();
        availablePoolElement = new LinkedList<>();
        taskExecutor = new TaskExecutor();
        for (int i = 0; i < count; i++) availablePoolElement.offer(i);
        taskExecutor.start();
    }

    public boolean addTask(Runnable task) {
        return threadQueue.offer(task);
    }

    void releaseTask(int taskNumber) {
        availablePoolElement.offer(taskNumber);
    }

    public void shutDown() {
        shutDown = true;
    }

    class TaskExecutor extends Thread {
        @Override
        public void run() {
            while (!shutDown || threadQueue.size() > 0) {
                if (availablePoolElement.size() > 0)
                    pool[availablePoolElement.poll()].setTask(threadQueue.poll());
                Thread.yield();
            }
            for (ThreadPoolTask task : pool) task.shutDown();

        }
    }
}

