package ru.lokyanvs.threadPool;

class ThreadPoolTask extends Thread {
    private Runnable task;
    private final int taskNumber;
    private final MyThreadPool mtp;
    private boolean done;
    private boolean shutDown;

    public ThreadPoolTask(int taskNumber, MyThreadPool mtp) {
        this.taskNumber = taskNumber;
        this.mtp = mtp;
    }

    @Override
    public void run() {
        while (!shutDown) {
            if (!done && task != null) {
                task.run();
                done = true;
                synchronized (mtp) {
                    mtp.releaseTask(taskNumber);
                }
            }
            Thread.yield();
        }
    }

    void setTask(Runnable newTask) {
        task = newTask;
        done = false;
    }

    void shutDown() {
        shutDown = true;
    }
}