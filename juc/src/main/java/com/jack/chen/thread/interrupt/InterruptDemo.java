package com.jack.chen.thread.interrupt;

import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {

    static volatile boolean isStop = false; //volatile表示的变量具有可见性

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        Thread t1 = new Thread(()-> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " isInterrupted()的值被改为true，t1程序停止");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "\t hello interrupt");
            }
        }, "t1");

        t1.start();

        System.out.println("t1 isInterrupted状态:" + t1.isInterrupted());

        new Thread(()->{
            t1.interrupt();//向t1线程发出中断协商请求
        }, "t2").start();

    }

    private static void m2_atomicboolean() {
        new Thread(()->{
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean被设置为true，程序结束");
                    break;
                }
                System.out.println("------hello atomicBoolean");
            }
        }, "t1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " \t 开始运行");
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1() {
        new Thread(()->{
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被设置为true，线程中断");
                    break;
                }
                System.out.println("------hello volatile");
            }
        }, "t1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " \t 开始运行");
            isStop = true;
        }, "t2").start();
    }

}
