package com.lwnull.base.thread.pool.threadPoolExecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();

//        threadPoolExecutorTest.test1();

//        threadPoolExecutorTest.test2();

//        threadPoolExecutorTest.test3();

        threadPoolExecutorTest.test4();

    }

    private void test4() {
        ExecutorService executorService = new ThreadPoolExecutor(3,
                5,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i=0; i<8; i++) {
            int finalI = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "======>办理业务 i="+ finalI);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * 问：以下程序会输出几次线程名称？
     * 答：线程名称会被打印3次。题目解析：线程池第一次执行任务时，会创建一个线程并执行任务；第2个任务执行时，因没有多余空闲的线程，所以进入任务列表；
     * 第3个任务执行时，也是进入任务列表等待；由于任务队列只够放两个等待任务，所以第4个开始的任务就被丢弃。于是就打印了3次线程名称。
     */
    private void test3() {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1,
                10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2),
                new ThreadPoolExecutor.DiscardPolicy());
        threadPool.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 打印线程名称
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务执行完毕");
                }
            });

        }

    }

    private void test2() {
        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(100));

        for (int i = 0; i < 20; i++) {
            System.out.println(" i = " + i);

            int finalI = i;
            threadPoolExecutor.execute(() -> {
                System.out.println("I'm " + finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("sleep 被打断，发生异常，异常消息：" + e.getMessage());
                }
                System.out.println(finalI + " 执行完成");
            });

        }

        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("线程池任务数：" + poolSize);

//        threadPoolExecutor.shutdown();//不会立即终止线程池，而是要等所有任务队列中的任务都执行完后才会终止

        //未执行的任务
        List<Runnable> unrunTask = threadPoolExecutor.shutdownNow(); // 执行该方法，线程池的状态立刻变成 STOP 状态，并试图停止所有正在执行的线程，不再处理还在池队列中等待的任务，执行此方法会返回未执行的任务。
        System.out.println("未执行任务数：" + unrunTask.size());
//        threadPoolExecutor.execute(() -> { System.out.println("I'm Java."); });
    }

    private void test1() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1,
                10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2),
                new MyThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("start run i:" + finalI + " ==========================================");
                    System.out.println("        " + Thread.currentThread().getName() + " " + new Date());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end run i:" + finalI + "==========================================");
                }
            });
        }

    }

 }

class MyThreadFactory implements ThreadFactory {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = "MyThread" + count.addAndGet(1);
        t.setName(threadName);
        System.out.println("创建线程 count:" + count);
        return t;
    }

}
