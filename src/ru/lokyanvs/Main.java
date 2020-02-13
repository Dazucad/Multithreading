package ru.lokyanvs;

import ru.lokyanvs.threadPool.MyThreadPool;

public class Main {

    public static void main(String[] args) {
        MyThreadPool mtp = new MyThreadPool(2);
        for (int i = 0; i < 40; i++)
            mtp.addTask(() -> {
                System.out.println("Начало задачи " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Конец задачи " + Thread.currentThread().getName());
            });
        mtp.shutDown();
        System.out.println("Конец main");
    }
}
