package com.jack.chen.thread;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {

        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(()->{
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + " \t 持有A锁，希望持有B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "\t 持有B锁");
                }
            }
        }, "T1").start();

        new Thread(()->{
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + " \t 持有B锁，希望持有A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "\t 持有A锁");
                }
            }
        }, "T2").start();

    }

}
