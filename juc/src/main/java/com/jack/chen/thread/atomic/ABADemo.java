package com.jack.chen.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        new Thread(() -> {

            int stamp = atomicStampedReference.getStamp();

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "\t 首次版本号:" + stamp);

            atomicStampedReference.compareAndSet(100, 101, stamp, stamp+1);

            stamp = atomicStampedReference.getStamp();

            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号:" + stamp);

            atomicStampedReference.compareAndSet(101, 100, stamp, stamp+1);

            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号:" + atomicStampedReference.getStamp());


        }, "t1").start();


        new Thread(() -> {

            int stamp = atomicStampedReference.getStamp();

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean b = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);

            stamp = atomicStampedReference.getStamp();

            System.out.println(Thread.currentThread().getName() + "\t b:" + b + "\t " + atomicStampedReference.getReference() + "\t stamp:" + stamp);

        }, "t2").start();

    }

    /**
     * t1	 首次版本号:1
     * t1	 第2次版本号:2
     * t1	 第2次版本号:3
     * t2	 b:false	 100	 stamp:3
     */

    private static void abaHappen() {

        new Thread(()->{

            atomicInteger.compareAndSet(100, 101);

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicInteger.compareAndSet(101, 100);

        }, "t1").start();

        new Thread(()->{

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean b = atomicInteger.compareAndSet(100, 101);

            System.out.println(b+"\t" + atomicInteger.get());

        }, "t1").start();

    }

}
