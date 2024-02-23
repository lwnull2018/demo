package com.jack.chen.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 借鉴CAS思想实现自旋锁
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t ------ come in");
        //没有被线程持有，则本线程持有，否则自旋
        while (!atomicReference.compareAndSet(null, thread)) {

        }
        System.out.println(thread.getName() + "\t ------ 成功获得锁");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        //如果是本线程持有的，则释放
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t ------ 成功释放锁");
    }

    /**
     * 通过CAS完成自旋锁，A线程先进来调用国lock方法自己持有锁5秒钟，B随后进来后发现当前有线程持有锁，所以只能通过自旋等待，直到A释放锁后B随后抢到。
     * @param args
     */
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.lock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            spinLockDemo.unlock();
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            spinLockDemo.lock();
            spinLockDemo.unlock();
        }, "B").start();

    }

}
