package ru.lokyanvs.threadPool;

class ThreadPoolTask extends Thread {
    private final MyThreadPool mtp;

    ThreadPoolTask(MyThreadPool mtp) {
        this.mtp = mtp;
    }

    @Override
    public void run() {
        while (!mtp.isShutDown()) {
            Runnable task;
            if ((task = mtp.getTask()) != null)
                task.run();
        }
    }

}