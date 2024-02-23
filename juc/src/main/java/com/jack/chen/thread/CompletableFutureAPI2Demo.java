package com.jack.chen.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPI2Demo {

    public static void main(String[] args) {

        // 2. 使用自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                //业务耗时1秒钟
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---步骤1---");
            return 1;
        }, threadPool).handle((v, e) -> {
            System.out.println("---步骤2---");
            int i = 10/0;
            return v + 2;
        }).handle((v, e) -> {
            System.out.println("---步骤3---");
            if (null != e) {
                System.out.println("---上一步发生异常---");
                return 3;
            }
            return v + 3;
        }).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("得到计算结果：" + v);
            } else {
                System.out.println("异常消息：" + e.getCause() + "\t" + e.getMessage());
            }
        });

        System.out.println(Thread.currentThread().getName() + "---主线程忙其他事情去了");

        threadPool.shutdown();

        //1. 若没有使用自定义线程池，默认使用ForkJoinPool，主线程结束了，也会同时关注它，所以没等待的话CompletableFuture里的内容可能不会执行
        /*try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private static void test1() {

        // 2. 使用自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                //业务耗时1秒钟
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---步骤1---");
            return 1;
        }, threadPool).thenApply(f -> {
            System.out.println("---步骤2---");
            int i = 10/0;
            return f + 2;
        }).thenApply(f -> {
            System.out.println("---步骤3---");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("得到计算结果：" + v);
            } else {
                System.out.println("异常消息：" + e.getCause() + "\t" + e.getMessage());
            }
        });

        System.out.println(Thread.currentThread().getName() + "---主线程忙其他事情去了");

        threadPool.shutdown();

        //1. 若没有使用自定义线程池，默认使用ForkJoinPool，主线程结束了，也会同时关注它，所以没等待的话CompletableFuture里的内容可能不会执行
        /*try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
