package com.lwnull.base.thread.pool.executors;

import java.util.Random;
import java.util.concurrent.*;

/**
 * ScheduledExecutorService 异常处理的正确姿势
 */
public class SESDemo {

    private static final ScheduledExecutorService executorService =
            Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {

        Random random = new Random();

        Runnable task = () -> {
            int i = random.nextInt(50);
            System.out.println("i="+i);
            if (i % 3 == 0) {
                throw new RuntimeException("发生异常");
            }
        };

        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            future = executorService.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);
        }

    }

    private static void test1() {

        Random random = new Random();

        executorService.scheduleWithFixedDelay(() -> {
            try {
                int i = random.nextInt(50);
                System.out.println("i="+i);
                if (i % 3 == 0) {
                    throw new RuntimeException("发生异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

}
