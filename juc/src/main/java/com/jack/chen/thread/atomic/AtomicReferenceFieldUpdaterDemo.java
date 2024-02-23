package com.jack.chen.thread.atomic;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar {

    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    public void init(MyVar myVar) {
        if (atomicReferenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {

            System.out.println(Thread.currentThread().getName() + "\t" + "开始初始化......");

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "\t 完成初始化工作......");
        } else {
            System.out.println(Thread.currentThread().getName() + "\t 已经有其他线程完成初始化工作");
        }
    }

}

/**
 * 需求：多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作
 * 要求只能被初始化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {

        MyVar myVar = new MyVar();

        for (int i = 1; i <= 5; i++) {
            new Thread(()-> {

                myVar.init(myVar);

            }, String.valueOf(i)).start();
        }

    }

}
/**
 * 1	开始初始化......
 * 2	 已经有其他线程完成初始化工作
 * 4	 已经有其他线程完成初始化工作
 * 3	 已经有其他线程完成初始化工作
 * 5	 已经有其他线程完成初始化工作
 * 1	 完成初始化工作......
 */