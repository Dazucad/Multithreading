package ru.lokyanvs;

import ru.lokyanvs.threadPool.MyThreadPool;

public class Main {

    public static void main(String[] args) {
        MyThreadPool mtp = new MyThreadPool(4);
        for (int i = 0; i < 16; i++)
            mtp.addTask(() -> {
                System.out.println("Начало задачи " + Thread.currentThread().getName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Конец задачи " + Thread.currentThread().getName());
            });
        mtp.shutDown();
        System.out.println("Конец main");
    }
}
