package com.lwnull.base.thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 实现生产者消费者 (synchronized版本)
 * 用synchronized对存储加锁，然后用object原生的wait() 和 notify()做同步。
 */
public class ProducerAndConsumer {

    private final int MAX_LEN = 10;
    private Queue<Integer> queue = new LinkedList<Integer>();

    class Producer extends Thread {
        @Override
        public void run() {
            producer();
        }

        private void producer() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == MAX_LEN) {
                        System.out.println("队列满了");
                        queue.notify();
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(1);
                    queue.notify();
                    System.out.println("生产者生产一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }

        private void consumer() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        System.out.println("当前队列空了");
                        queue.notify();
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.poll();
                    queue.notify();
                    System.out.println("消费者消费一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer pc = new ProducerAndConsumer();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();

        producer.start();
        consumer.start();
    }

}
