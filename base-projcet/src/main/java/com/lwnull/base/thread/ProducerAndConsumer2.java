package com.lwnull.base.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者(lock版实现)
 * 使用了condition做线程之间的同步。
 */
public class ProducerAndConsumer2 {

    private final int MAX_LEN = 10;
    private Queue<Integer> queue = new LinkedList<Integer>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    class Producer extends Thread {

        @Override
        public void run() {
            producer();
        }

        private void producer() {
            while (true) {
                lock.lock();//请求锁
                try {
                    while(queue.size() == MAX_LEN) {
                        System.out.println("当前队列满了");
                        try {
                           condition.await();//使当前线程加入 await() 等待队列中，并释放当锁，当其他线程调用signal()会重新请求锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(1);
                    condition.signal();//唤醒一个在 await()等待队列中的线程
                    System.out.println("生产任务生产一条消息，当前队列长度：" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            cunsumer();
        }

        private void cunsumer() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        System.out.println("当前队列空了");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    condition.signal();//唤醒等待线程
                    System.out.println("消费者消费一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer2 pc = new ProducerAndConsumer2();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();
        producer.start();
        consumer.start();
    }
}
