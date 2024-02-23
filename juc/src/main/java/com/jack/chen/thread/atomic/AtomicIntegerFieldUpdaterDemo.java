package com.jack.chen.thread.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount {
    public volatile int money;

    //创建一个更新器
    AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public  void add(BankAccount bankAccount) {
        atomicIntegerFieldUpdater.getAndIncrement(bankAccount);
    }
}

/**
 * 需求：10个线程并发操作，每个线程操作1000次
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount();

        CountDownLatch countDownLatch = new CountDownLatch(10);

        long t1  = System.currentTimeMillis();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 1000; j++) {
                        bankAccount.add(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis();

        System.out.println(Thread.currentThread().getName() + "\t" + bankAccount.money + "\t" + (t2 - t1));
    }
}
/**
 * main	10000	3
 */