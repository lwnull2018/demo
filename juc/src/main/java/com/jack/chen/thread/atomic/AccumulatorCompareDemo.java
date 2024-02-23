package com.jack.chen.thread.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber {

    long number = 0;

    AtomicLong atomicLong = new AtomicLong();
    LongAdder longAdder = new LongAdder();
    LongAccumulator longAccumulator = new LongAccumulator((x,y) -> { return x+y; }, 0);

    public synchronized void clickSynchronized() {
        number++;
    }

    public void incrementByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    public void incrementByLongAddre() {
        longAdder.increment();
    }

    public void incrementByLongAccumulator() {
        longAccumulator.accumulate(1);
    }

}

/**
 * 需求：50个线程，每个线程100w次点赞，总点赞数出来
 */
public class AccumulatorCompareDemo {

    public static final int _1W = 10000;
    public static final int THREAD_NUM = 50;


    public static void main(String[] args) {

        ClickNumber clickNumber = new ClickNumber();

        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUM);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUM);

        long start = 0L;
        long end = 0L;

        start = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickSynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }

            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();

        System.out.println("clickSynchronized 耗时：" + (end - start) + "\t" + clickNumber.number);


        start = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.incrementByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }

            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();

        System.out.println("incrementByAtomicLong 耗时：" + (end - start) + "\t" + clickNumber.atomicLong.get());

        start = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.incrementByLongAddre();
                    }
                } finally {
                    countDownLatch3.countDown();
                }

            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();

        System.out.println("incrementByLongAddre 耗时：" + (end - start) + "\t" + clickNumber.longAdder.longValue());

        start = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.incrementByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }

            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch4.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();

        System.out.println("incrementByLongAccumulator 耗时：" + (end - start) + "\t" + clickNumber.longAccumulator.longValue());

    }
}
/**
 * clickSynchronized 耗时：5073	50000000
 * incrementByAtomicLong 耗时：3439	50000000
 * incrementByLongAddre 耗时：97	50000000
 * incrementByLongAccumulator 耗时：81	50000000
 */
