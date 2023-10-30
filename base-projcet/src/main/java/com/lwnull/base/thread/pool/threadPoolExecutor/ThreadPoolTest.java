package com.lwnull.base.thread.pool.threadPoolExecutor;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 重写BlockingQueue的offer
 *
 * CachedThreadPool 和 FixedThreadPool 都有可能导致内存溢出，前者是由于线程数过多，后者是由于队列任务过多。而究其根本就是因为任务生产速度远大于线程池处理任务的速度。
 *
 * 所以有一个想法就是让生产任务的线程在任务处理不过来的时候休息一会儿——也就是阻塞住任务生产者。
 *
 * 但是前面提到过ThreadPoolExecutor内部将任务提交到队列时，使用的是不阻塞的offer方法。
 *
 * 我提供的第一种方式是：重写offer方法把它变成阻塞式。
 */
public class ThreadPoolTest {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2) {
                    @Override public boolean offer(Runnable runnable) {
                        try {
                            super.put(runnable); // 使用阻塞的put重写offer方法
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
        );

        threadPool.submit(new Task(1));
        System.out.println("task 1 submitted");
        threadPool.submit(new Task(2));
        System.out.println("task 2 submitted");
        threadPool.submit(new Task(3));
        System.out.println("task 3 submitted");
        threadPool.submit(new Task(4));
        System.out.println("task 4 submitted");

        // 第五个任务要等前面任务完成了，才能提交成功
        threadPool.submit(new Task(5));
        System.out.println("task 5 submitted");
        threadPool.submit(new Task(6));
        System.out.println("task 6 submitted");
        threadPool.shutdown();
    }

    private static class Task implements Runnable {
        private int taskId;

        Task(int taskId) {
            this.taskId = taskId;
        }

        @Override public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ignore) {
            }
            System.out.println("task " + taskId + " end");
        }
    }


}
