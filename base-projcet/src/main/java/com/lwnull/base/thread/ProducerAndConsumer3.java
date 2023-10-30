package com.lwnull.base.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者(BlockingQueue实现)
 *
 */
public class ProducerAndConsumer3 {

    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);

    class Producer extends Thread {
        @Override
        public void run() {
            producer();
        }

        private void producer() {

        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }

        private void consumer() {

        }
    }


}
