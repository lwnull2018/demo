package com.lwnull.base.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程安全计数类，输出结果不会有重复的
 * Created by abc@123.com ON 2018/10/31.
 */
public class SafeThreadCount implements Runnable {
    private final AtomicLong count = new AtomicLong(0);

    public void run() {
        for (int i = 0; i < 10; i++){
            count.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + "  : " +count.intValue());
        }
    }

    public static void main(String[] args){
        ExecutorService pool = Executors. newCachedThreadPool();
        SafeThreadCount countThread = new SafeThreadCount();
        for (int i = 0;i < 10; i++){
            pool.execute(countThread);
        }
    }

}
