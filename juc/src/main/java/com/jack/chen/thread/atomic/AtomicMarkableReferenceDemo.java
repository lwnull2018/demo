package com.jack.chen.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {

    public static void main(String[] args) {

        AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100, false);
        
        new Thread(()->{
            boolean marked = atomicMarkableReference.isMarked();

            System.out.println(Thread.currentThread().getName() + "\t 默认标记：" + marked);

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean b = atomicMarkableReference.compareAndSet(100, 1000, marked, !marked);

            System.out.println(Thread.currentThread().getName() + "\t" + atomicMarkableReference.getReference() + "\t" + b + "\t" + atomicMarkableReference.isMarked());

        }, "t1").start();

        new Thread(()->{
            boolean marked = atomicMarkableReference.isMarked();

            System.out.println(Thread.currentThread().getName() + "\t 默认标记：" + marked);

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean b = atomicMarkableReference.compareAndSet(100, 2000, marked, !marked);

            System.out.println(Thread.currentThread().getName() + "\t" + atomicMarkableReference.getReference() + "\t" + b + "\t" + atomicMarkableReference.isMarked());
            
        }, "t2").start();

    }
}
/**
 * t1	 默认标记：false
 * t2	 默认标记：false
 * t1	1000	true	true
 * t2	1000	false	true
 */