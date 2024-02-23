package com.jack.chen.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionDemo {

    /**
     * 运行结果：
     * t1	 come in
     * t1	 上锁
     * t2	 come in
     * t2	 发出唤醒通知
     * t1	 等待被唤醒
     *
     * @param args
     */
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");

            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "\t 上锁");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t 等待被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }, "t1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                lock.lock();
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t 发出唤醒通知");
            } finally {
                lock.unlock();
            }
        }, "t2").start();

    }

}
