package com.jack.chen.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureFastDemo {

    /**
     * 代理合并写在一起
     * @param args
     */
    public static void main(String[] args) {
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 任务1启动...");
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + " 任务2启动...");
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        }), (a, b) -> {
            return a + b;
        });

        System.out.println("计算结果合并 x+y = " + task.join());
    }

    /**
     * 演示两个任务的计算结果进行合并
     */
    private static void test2() {
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 任务1启动...");
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 任务2启动...");
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> resultFuture = task1.thenCombine(task2, (x, y) -> {
            System.out.println("x=" + x + " , y = " + y);
            return x + y;
        });

        System.out.println("计算结果合并 x+y = " + resultFuture.join());

    }

    private static void test() {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("---come in PlayA");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "PlayA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("---come in PlayB");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "PlayB";
        });

        CompletableFuture<Object> resultFuture = playA.applyToEither(playB, f -> {
            return f + " is winner";
        });

        System.out.println(Thread.currentThread().getName() + "\t" + resultFuture.join());
    }

}
