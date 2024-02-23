package com.jack.chen.thread;

import java.util.concurrent.*;

public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorPool = Executors.newFixedThreadPool(2);

        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " --- come in.");
                int result = ThreadLocalRandom.current().nextInt(100);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("---休息1秒种出结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*if (result > 10) {
                    int i = 10 / 0;
                }*/
                return result;
            }, executorPool).whenComplete((v, e) -> {
                if (null == e) {
                    System.out.println("------计算完成，更新系统值：" + v);
                }
            }).exceptionally(e -> {
                System.out.println("异常情况 " + e.getCause() + "\t" + e.getMessage());
                return null;
            });

            System.out.println(Thread.currentThread().getName() + "线程先忙其他任务");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorPool.shutdown();
            System.out.println("关闭线程池");
        }

    }

    private static void future2() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " --- come in.");
            int result = ThreadLocalRandom.current().nextInt(100);
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("---休息1秒种出结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("------计算完成，更新系统值：" + v);
            }
        }).exceptionally(e -> {
            System.out.println("异常情况 " + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "线程先忙其他任务");

        //主线程不要立即结束，否则CompletableFuture使用的默认线程池会立即关闭，暂停3秒线程，等待CompletableFuture里面的内容执行完成
        TimeUnit.SECONDS.sleep(3);
    }

    private static void future1() throws ExecutionException, InterruptedException  {

        ExecutorService executors = Executors.newFixedThreadPool(2);

        //无返回值的异步执行方法
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            //没有指定Executor 默认是用ForkJoinPool.commonPool 作为线程池执行异步代码
            System.out.println(Thread.currentThread().getName() + " \t running...");
        }, executors);

        System.out.println(voidCompletableFuture.get());

        //有返回值的异步执行方法
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //没有指定Executor 默认是用ForkJoinPool.commonPool 作为线程池执行异步代码
            System.out.println(Thread.currentThread().getName() + " \t running...");
            return "completableFuture over.";
        }, executors);

        System.out.println(completableFuture.get());

        //关闭线程池
        executors.shutdown();

    }

}
