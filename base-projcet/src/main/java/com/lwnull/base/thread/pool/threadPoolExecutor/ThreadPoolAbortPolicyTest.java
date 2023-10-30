package com.lwnull.base.thread.pool.threadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略示例
 * AbortPolicy——抛出RejectedExecutionException异常的方式拒绝任务。默认拒绝策略
 * DiscardPolicy——什么都不干，静默地丢弃任务
 * DiscardOldestPolicy——把队列中最老的任务拿出来扔掉
 * CallerRunsPolicy——在任务提交的线程把任务给执行了
 *
 * 可以采用"温柔"一点的方式，任务被拒绝后进入阻塞，直到线程池中任务被执行完成了，队列中有多余空间，调用方才会返回
 */
public class ThreadPoolAbortPolicyTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                new BlockCallerPolicy());

        threadPoolExecutor.submit(new Task(1));
        System.out.println("task 1 submitted!");
        threadPoolExecutor.submit(new Task(2));
        System.out.println("task 2 submitted!");
        threadPoolExecutor.submit(new Task(3));
        System.out.println("task 3 submitted!");
        threadPoolExecutor.submit(new Task(4));
        System.out.println("task 4 submitted!");
        threadPoolExecutor.submit(new Task(5));
        System.out.println("task 5 submitted!");
        threadPoolExecutor.submit(new Task(6));
        System.out.println("task 6 submitted!");
        threadPoolExecutor.shutdown();
    }

    /**
     * 重写拒绝策略，拒绝后进入阻塞，直到队列有多余空间，才返回
     */
    private static class BlockCallerPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //拒绝后调用put()，让任务提交者阻塞
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Task implements Runnable {

        private int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task " + taskId + " end");
        }

    }

}
