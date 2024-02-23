package com.jack.chen.thread.atomic;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyCount {

    private AtomicInteger count = new AtomicInteger(0);

    public synchronized void increase() {
        count.getAndIncrement();
    }

    public int getCount() {
        return count.get();
    }

}


public class NoAtomicDemo {

    public static void main(String[] args) {

        MyCount myCount = new MyCount();

        for (int i=0; i<10; i++) {
            new Thread(()->{
                for (int j=0; j<1000; j++) {
                    myCount.increase();
                }
            }, "t1").start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t count:" + myCount.getCount());

    }

}
