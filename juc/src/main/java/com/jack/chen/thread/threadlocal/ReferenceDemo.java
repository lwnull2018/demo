package com.jack.chen.thread.threadlocal;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

class MyObject {

    /**
     * 对象被垃圾回收前会调用些方法，只是演示需要，实际开发中一般不需要重写该方法
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println(Thread.currentThread().getName() + "\t ------ invoke finalize method!!!");
    }

}

public class ReferenceDemo {

    public static void main(String[] args) {

//        weakReference();

        softReference();
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());

        System.out.println("gc before 内存足够用，weakReference: " + weakReference);

        System.gc();//手动开启垃圾回收

        System.out.println("gc after 内存足够用，weakReference: " + weakReference);
    }


    private static void softReference() {
        MyObject myObject = new MyObject();
        SoftReference<MyObject> softReference = new SoftReference<>(myObject);

        System.out.println("softReference:" + softReference.get());

        System.gc();//手动开启垃圾回收

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc after 内存足够用，softReference:" + softReference.get());

        //设置 VM Options : -Xms10m -Xmx10m
        try {
            byte[] bytes = new byte[20 * 1024 * 1024];//设置个20M的对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("gc after 内存不足，softReference:" + softReference.get());
        }

        System.out.println("=================");

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];//设置个20M的对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("gc after 内存不足，softReference:" + softReference.get());
        }
    }

}
/**
 * gc before 内存足够用，weakReference: java.lang.ref.WeakReference@24d46ca6
 * gc after 内存足够用，weakReference: java.lang.ref.WeakReference@24d46ca6
 * Finalizer	 ------ invoke finalize method!!!
 */
