package com.lwnull.base.thread.count_down_latch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {


    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        Increment increment = new Increment(countDownLatch);
        Decrement decrement = new Decrement(countDownLatch);

        Thread thread1 = new Thread(increment);
        Thread thread2 = new Thread(decrement);

        thread1.start();
        thread2.start();

    }

}

class Increment implements Runnable {
    CountDownLatch countDownLatch;

    public Increment(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("Increment await...");
            countDownLatch.await();//当count的值为0时，这里的等待会被唤醒，继续执行
            System.out.println("Increment 继续执行...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Decrement implements Runnable {
    CountDownLatch countDownLatch;

    public Decrement(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            for (long i = countDownLatch.getCount();  i > 0; i--) {
                Thread.sleep(1000);
                System.out.println("countDown");
                countDownLatch.countDown();//当count的值减到0时，会唤醒await的等待线程继续执行
            }
            System.out.println("Decrement执行完毕");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}