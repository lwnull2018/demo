package com.jack.chen.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {

    static int length = 5;
    //两种构造方法
    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(length);
//    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});

    public static void main(String[] args) {

        for (int i = 0; i < length; i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        System.out.println();

        int index = 0;

        //对指定索引设置值
        atomicIntegerArray.set(index, 2024);

        //获取指定索引的值
        System.out.println(atomicIntegerArray.get(index));

        //获取指定索引的值并递增1
        atomicIntegerArray.getAndIncrement(index);
        System.out.println(atomicIntegerArray.get(index));

    }
}
/**
 * 0
 * 0
 * 0
 * 0
 * 0
 *
 * 2024
 * 2025
 */
