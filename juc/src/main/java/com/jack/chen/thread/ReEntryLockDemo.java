package com.jack.chen.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntryLockDemo {

    public static void main(String[] args) {
        final Object object = new Object();

        /**
         * 外层调用
         * 中层调用
         * 内层调用
         */
        new Thread(()->{
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " \t 外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t 中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t 内层调用");
                    }
                }
            }
        }, "T1").start();;

        /**
         * 注意；加锁几次，就需要解锁几次
         *
         */
        Lock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "\t 中层调用");
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "\t 内层调用");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "T2").start();

    }

}
