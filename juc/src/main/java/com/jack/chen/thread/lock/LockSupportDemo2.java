package com.jack.chen.thread.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo2 {

    /**
     * 运行结果：
     * t1	 come in
     * t1	 被唤醒
     * t2	 发出许可
     *
     * @param args
     */
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");

            LockSupport.park();

            System.out.println(Thread.currentThread().getName() + "\t 被唤醒");

        }, "t1");
        t1.start();

        new Thread(()->{
            LockSupport.unpark(t1);//向t1线程发放许可
            System.out.println(Thread.currentThread().getName() + "\t 发出许可");
        }, "t2").start();

    }

}
