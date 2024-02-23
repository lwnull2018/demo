package com.jack.chen.thread;

import java.util.concurrent.*;

public class FuturePoolDemo {

    public static void main(String[] args) throws Exception {
        //
//        m1();

        m2();
    }

    static  void m2() throws Exception {

        long t1 = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        FutureTask task1 = new FutureTask(() -> {
            Thread.sleep(300);
            return "t1 执行完";
        });

        FutureTask task2 = new FutureTask(() -> {
            Thread.sleep(300);
            return "t2 执行完";
        });

        executorService.submit(task1);
        executorService.submit(task2);

        System.out.println(task1.get());
        System.out.println(task2.get());

        Thread.sleep(500);

        long t2 = System.currentTimeMillis();

        System.out.println("耗时：" + (t2 - t1));

        executorService.shutdown();
    }

    /**
     * 3个子任务，1个主线程耗时多少
     */
    static void m1() {
        long t1 = System.currentTimeMillis();

        try {
            Thread.sleep(300);
            Thread.sleep(300);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e);
        }

        long t2 = System.currentTimeMillis();

        System.out.println("耗时：" + (t2 - t1));
    }

}
