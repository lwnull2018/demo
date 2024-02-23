package com.jack.chen.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 1. 中断标志位默认为false
 * 2. t2对t1发出中断协商  t1.interrupt();
 * 3. 中断标志位为true： 正常情况 程序停止
 *    中断标志位为true  异常情况，InterruptedException，将会把中断状态清除，中断标志位为false
 * 4. 需要在catch块中，再次调用interrupt()方法将中断标志位设置为false;
 */
public class InterruptDemo3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {

            while (true) {

                if (Thread.currentThread().isInterrupted()) {//中断标志位为true，则跳出循环
                    System.out.println(Thread.currentThread().getName() + "\t 中断标记位为：" + Thread.currentThread().isInterrupted());
                    break;
                }

                /**
                 * sleep方法抛出InterruptedException后，中断标识也被清空置为false，如果没有在
                 * catch方法中调用interrupt方法再次将中断标识置为true，这将导致无限循环了
                 */
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException 中断状态：" + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();//发生中断异常需要重新中断，否则会进入死循环
                    e.printStackTrace();
                }

                System.out.println("------hello InterruptDemo3");
            }

        }, "t1");
        t1.start();

        new Thread(()-> t1.interrupt() , "t2").start();

    }

}
