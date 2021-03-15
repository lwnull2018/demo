package com.lwnull.base.volatiledemo;

/**
 *
 * Created by lwnull2018@gmail.com ON 2018/11/25.
 */
public class JoinThread2 extends Thread {
    public static int n = 0;

    /**
     * 通过 synchronized 关键字来同步，保证多线程情况下 n++ 原子性操作
     */
    public static synchronized void inc() {
        n++;
    }

    public void run() {
        for (int i = 0; i < 10; i++)
            try {
                inc();  //  n = n + 1 改成了 inc();
                sleep(3);  //  为了使运行结果更随机，延迟3毫秒

            } catch (Exception e) {
            }
    }

    public static void main(String[] args) throws Exception {

        Thread threads[] = new Thread[100];
        for (int i = 0; i < threads.length; i++)
            //  建立100个线程
            threads[i] = new JoinThread2();
        for (int i = 0; i < threads.length; i++)
            //  运行刚才建立的100个线程
            threads[i].start();
        for (int i = 0; i < threads.length; i++)
            //  100个线程都执行完后继续
            threads[i].join();
        System.out.println(" n= " + JoinThread2.n);
    }
}