package com.jack.chen.thread.interrupt;

public class InterruptDemo4 {

    /**
     * 运行结果：
     * main	false
     * main	false
     * ------1
     * ------2
     * main	true
     * main	false
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());

        System.out.println("------1");
        Thread.currentThread().interrupt();
        System.out.println("------2");

        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
    }

}
