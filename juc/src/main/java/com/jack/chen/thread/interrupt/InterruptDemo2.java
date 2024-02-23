package com.jack.chen.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 演示interrupt设置为true，并不表示线程已经中断
 */
public class InterruptDemo2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {

            for (int i=1; i<300; i++) {
                System.out.println("i=" + i + " 中断标记位：" + Thread.currentThread().isInterrupted());
            }

            System.out.println(Thread.currentThread().getName() + "\t interrupt状态02：" + Thread.currentThread().isInterrupted());

        }, "t1");

        t1.start();

        System.out.println("t1线程的默认中断标记位0:" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();

        System.out.println("t1线程的中断标记位01:" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //500毫秒后，t1线程已经不活动了，不会产生任何影响
        System.out.println("t1线程interrupt()后的中断标记位03:" + t1.isInterrupted());

    }

}
