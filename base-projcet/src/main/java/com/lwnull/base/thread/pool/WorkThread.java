package com.lwnull.base.thread.pool;

/**
 * 一个实现Runnable接口的普通线程类
 * Created by abc@123.com ON 2018/10/8.
 */
public class WorkThread implements Runnable {
    private String command;

    public WorkThread(String s) {
        this.command = s;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);

        processCommand();

        System.out.println(Thread.currentThread().getName() + " End.");
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }

}
