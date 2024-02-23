package com.jack.chen.thread;


import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟3个窗口售卖50张票的场景
 */
class Ticket {

    int number = 50;
    ReentrantLock reentrantLock = new ReentrantLock(true);//默认为false，非公平锁。公平也只是相对公平，不是绝对公平。

    void sale() {
        reentrantLock.lock();

        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第：\t" + (number--) + " , 还剩：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}

public class TicketSaleDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {for (int i=0; i<50; i++){ ticket.sale(); }}, "a").start();
        new Thread(() -> {for (int i=0; i<50; i++){ ticket.sale(); }}, "b").start();
        new Thread(() -> {for (int i=0; i<50; i++){ ticket.sale(); }}, "c").start();

    }

}
