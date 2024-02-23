package com.jack.chen.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPI3Demo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"---线程池---");
            return "123";
        }, threadPool).thenRunAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---第2步线程池---");
        }).thenRun(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---步骤3线程池---");
        });

        threadPool.shutdown();

        try {
            //主线程需要等待下，不然步骤2开始可能就不会输出，因为它是用的ForkJoinPool，来不及执行，主线程关闭，它就被关闭了
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void test2() {
        System.out.println(CompletableFuture.supplyAsync(() -> {return 1;}).thenRun(() -> {}).join());// null
        System.out.println(CompletableFuture.supplyAsync(() -> {return 1;}).thenAccept((f) -> {System.out.println(f);}).join());//1 null
        System.out.println(CompletableFuture.supplyAsync(() -> {return 1;}).thenApply((f) -> { return f + 2; }).join());//3
    }

    private static void test() {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenAccept(f -> {
            System.out.println(f);//3
        });
    }

}
