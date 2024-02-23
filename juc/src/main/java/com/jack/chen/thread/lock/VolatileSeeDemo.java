package com.jack.chen.thread.lock;

import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {

    /**
     * 运行结果：
     * t1	 ------ come in. flag=true
     * t1	 ------ flag被置为false，程序停止
     * main	 修改完成. flag=false
     */
    static volatile boolean flag = true;

    public static void main(String[] args) {

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t ------ come in. flag=" + flag);

            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t ------ flag被置为false，程序停止");

        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成. flag=" + flag);

    }

}
