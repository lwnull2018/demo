package com.lwnull.base.concurrent;

/**
 * 在没有同步的情况下共享变量(不要这么做)
 *
 * 虽然NoVisibility看起来会输出42，但事实上很可能输出0，或者根本无法终止，这是因为在代码中没有使用足够的同步机制，
 * 因此无法保证主线程写入的ready值和number值对于读线程来说是可见的。
 *
 * Created by abc@123.com ON 2018/10/31.
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        public void run(){
            while (!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args){
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
