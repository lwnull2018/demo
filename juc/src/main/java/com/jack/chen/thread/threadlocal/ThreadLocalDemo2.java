package com.jack.chen.thread.threadlocal;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 需求：需求变更：希望各自分灶吃饭，各凭销售本事提成，按照出单数各自统计-------比如房产中介销售都有自己的销售额指标，自己专属自己的，不和别人参和。
 */
class House {
    int saleCount = 0;

    public synchronized  void add() {
        saleCount++;
    }

    ThreadLocal<Integer> saleVolume =  ThreadLocal.withInitial(()->0);//初始销售额为0

    public void saleVolumeByThreadLocal() {
        saleVolume.set(saleVolume.get() + 1);//销售数量加1
    }
}

public class ThreadLocalDemo2 {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        House house = new House();

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                int size = new Random().nextInt(10) + 1;
                try {
                    for (int j = 1; j <= size; j++) {
                        house.add();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 销售房数：" + house.saleVolume.get());
                } finally {
                    countDownLatch.countDown();
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 总销售房数：" + house.saleCount);
    }
}
/**
 * 1	 销售房数：1
 * 2	 销售房数：9
 * 3	 销售房数：10
 * 4	 销售房数：4
 * 5	 销售房数：3
 * main	 总销售房数：27
 */
