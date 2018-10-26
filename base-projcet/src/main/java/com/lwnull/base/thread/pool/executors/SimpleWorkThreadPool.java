package com.lwnull.base.thread.pool.executors;

import com.lwnull.base.thread.pool.WorkThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用 Executors 框架创建了一个固定的线程池的测试程序
 *
 * 输出结果表明线程池中至始至终只有五个名为 "pool-1-thread-1" 到 "pool-1-thread-5" 的五个线程，这五个线程不随着工作的完成而消亡，会一直存在，
 * 并负责执行分配给线程池的任务，直到线程池消亡。
 *
 * 【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样 的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明:Executors 返回的线程池对象的弊端如下:
 * 1)FixedThreadPool 和 SingleThreadPool:
 * 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
 * 2)CachedThreadPool 和 ScheduledThreadPool:
 * 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。
 *
 * Created by lwnull2018@gmail.com ON 2018/10/8.
 */
public class SimpleWorkThreadPool {

    public static void main(String[] args) {
        //创建一个固定大小为5的线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkThread("" + i);
            executor.execute(worker);
        }

        executor.shutdown(); // This will make the executor accept no new threads and finish all existing threads in the queue

        while (!executor.isTerminated()) { // Wait until all threads are finish,and also you can use "executor.awaitTermination();" to wait
        }
        System.out.println("Finished all threads");
    }

}

