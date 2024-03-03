package com.jack.chen.thread.threadlocal;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 需求：5个销售卖房子，集团只关心销售总量的精确统计数
 */
class HouseSaler {

    int saleCount = 0;

    public synchronized void add() {
        saleCount++;
    }

}

public class ThreadLocalDemo {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        HouseSaler houseSaler = new HouseSaler();

        for (int i = 1; i <= 5; i++) {
                new Thread(()->{
                    try {
                        int size = new Random().nextInt(10) + 1;
                        System.out.println(Thread.currentThread().getName() + "\t 销售房数：" + size);
                        for (int j = 1; j <= size; j++) {
                            houseSaler.add();
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

        System.out.println(Thread.currentThread().getName() + "\t 总共销售房数：" + houseSaler.saleCount);

    }
}
/**
 * 1	 销售房数：3
 * 2	 销售房数：3
 * 3	 销售房数：8
 * 4	 销售房数：2
 * 5	 销售房数：7
 * main	 总共销售房数：23
 */
