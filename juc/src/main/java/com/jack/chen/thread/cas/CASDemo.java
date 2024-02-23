package com.jack.chen.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    /**
     * 运行结果：
     *  true	2024
     *  false	2024
     * @param args
     */
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.compareAndSet(10, 2024) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(10, 2024) + "\t" + atomicInteger.get());
    }
}
