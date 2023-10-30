package com.lwnull.base.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列简单使用
 * 3个生产者线程
 * 3个消费者线程
 */
public class MyBlockingQueue {

    static ArrayBlockingQueue abq = new ArrayBlockingQueue(3);

    public static void main(String[] args) {

        //生产者线程
        for (int i=0; i<3; i++) {
            new Thread(()-> producer(), "producerThread-" + i).start();
        }

        //消费者线程
        for (int i=0; i<3; i++) {
            new Thread(()-> consumer(), "consumerThread-" + i).start();
        }

    }

    /**
     * 生产者
     */
    private static void producer() {

        try {
            for (int i=0; i<100; i++) {
                abq.put(Thread.currentThread().getName() + "[" + i + "]");
                System.out.println(Thread.currentThread().getName() + "->sendMsg:" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 消费者
     */
    private static void consumer() {

        try {
            for (int i=0; i<100; i++) {
                Object take = abq.take();
                System.out.println(Thread.currentThread().getName() + "->receiveMsg:" + take);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
