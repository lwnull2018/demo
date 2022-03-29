package com.lwnull.base.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 非线程安全，输出结果可能会有重复的
 * Created by abc@123.com ON 2018/10/31.
 */
public class UnsafeThreadCount implements Runnable {
    private long count = 0;

    public void run() {
        for (int i = 0; i < 10; i++){
            ++count;
            System.out.println(Thread.currentThread().getName() + "  : " +count);
        }
    }

    public static void main(String[] args){
        ExecutorService pool = Executors. newCachedThreadPool();
        UnsafeThreadCount countThread = new UnsafeThreadCount();
        for (int i = 0;i < 10; i++){
            pool.execute(countThread);
        }
    }

}
