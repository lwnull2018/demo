package com.jack.chen.thread.lock;

import java.util.concurrent.TimeUnit;

public class LockSupportDemo {

    /**
     * 运行结果：
     * t1	 开始运行......
     * t2	 开始运行......
     * t2	 发送通知......
     * t1	 被唤醒......
     * @param args
     */
    public static void main(String[] args) {
        Object object = new Object();

        new Thread(()->{
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t 开始运行......");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t 被唤醒......");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t 开始运行......");
                object.notify();
                System.out.println(Thread.currentThread().getName() + "\t 发送通知......");
            }
        }, "t2").start();

    }

}
