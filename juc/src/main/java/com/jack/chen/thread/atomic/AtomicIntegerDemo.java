package com.jack.chen.thread.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();


    void add() {
        atomicInteger.getAndIncrement();
    }
}

public class AtomicIntegerDemo {

    static int SIZE = 50;

    public static void main(String[] args) {

        MyNumber myNumber = new MyNumber();
        //闭锁
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        long t1 = System.currentTimeMillis();

        for (int i = 1; i <= SIZE; i++) {

            new Thread(()->{

                try {
                    for (int j = 1; j <= 1000; j++) {
                        myNumber.add();
                    }
                } finally {
                    //递减计数器
                    countDownLatch.countDown();
                }

            }, String.valueOf(i)).start();

        }

        try {
            //让当前线程一直等待，直到计数器减为0，当计数器为0，线程立即通过，不需要通过休眠阻塞线程，最大可能缩减时间，不会浪费多余的时间
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis();

        System.out.println("耗时：" + (t2 - t1));

        /*try {
            //没有休眠，则主线程可能比上面的线程早执行，所以出现未计算完成就取值的情况，结果少于预期50000
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println(Thread.currentThread().getName() + "\t" + myNumber.atomicInteger.get());
    }
}
