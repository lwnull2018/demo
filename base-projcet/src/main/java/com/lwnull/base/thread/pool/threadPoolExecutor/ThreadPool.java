package com.lwnull.base.thread.pool.threadPoolExecutor;

import com.lwnull.base.thread.pool.WorkThread;

import java.util.concurrent.*;

/**
 * 总结一下线程池添加任务的整个流程：
 *
 *
 * 1、线程池刚刚创建是，线程数量为0；
 * 2、执行execute添加新的任务时会在线程池创建一个新的线程；
 * 3、当线程数量达到corePoolSize时，再添加新任务则会将任务放到workQueue阻塞队列；
 * 4、当阻塞队列已满放不下新的任务，再添加新任务则会继续创建新线程，但线程数量不超过maximumPoolSize；
 * 5、当线程数量达到maximumPoolSize时，再添加新任务则会抛出异常。
 *
 * Created by abc@123.com ON 2018/10/8.
 */
public class ThreadPool {

    public static void main(String args[]) throws InterruptedException {
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2,
                                                            4,
                                                              10,
                                                            TimeUnit.SECONDS,
                                                            new ArrayBlockingQueue<Runnable>(2),
                                                            threadFactory,
                                                            rejectionHandler);

        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        //submit work to the thread pool
        for (int i = 0; i < 10; i++) {
            executorPool.execute(new WorkThread("cmd-" + i));
        }

        Thread.sleep(30000);

        //shut down the pool
        executorPool.shutdown();

        //shut down the monitor thread
        Thread.sleep(5000);

        monitor.shutdown();

    }

}
