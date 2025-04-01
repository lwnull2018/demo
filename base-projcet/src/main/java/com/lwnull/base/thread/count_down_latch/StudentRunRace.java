package com.lwnull.base.thread.count_down_latch;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 应用CountDownLatch实现学生赛跑项目示例
 */
public class StudentRunRace {

    //1个裁判
    final CountDownLatch stopLatch = new CountDownLatch(1);
    //10个学生
    final CountDownLatch studentLatch = new CountDownLatch(10);

    //等待发枪令
    public void waitSignal() throws InterruptedException {
        System.out.println("选手"+Thread.currentThread().getName()+"正在等待裁判发布口令");
        //等待发枪令
        stopLatch.await();
        System.out.println("选手"+Thread.currentThread().getName()+"已接到发枪口令");
        long sleepTime = (long) (Math.random() * 10000);
        Thread.sleep(sleepTime);
        System.out.println("选手"+Thread.currentThread().getName()+"到达终点 耗时：" + sleepTime +" mills");
        //一位选手到达终点 减1
        studentLatch.countDown();
    }

    //等待结束
    public void waitStop() throws InterruptedException {
        System.out.println("[waitStop] " + Thread.currentThread().getName());
        Thread.sleep((long)(Math.random() * 1000));
        System.out.println("裁判"+Thread.currentThread().getName()+"即将发枪");
        //发枪
        stopLatch.countDown();
        System.out.println("裁判"+Thread.currentThread().getName()+"已发枪，等待所有选手到达终点");
        //等待所有选手到达终点
        studentLatch.await();
        System.out.println("所有选手已达到终点");
        System.out.println("裁判"+Thread.currentThread().getName()+"汇总成绩排名");
    }

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        StudentRunRace studentRunRace = new StudentRunRace();
        //创建10个学生线程
        for (int i=0; i < 10; i++) {
            Runnable runnable = ()->{
                try {
                    studentRunRace.waitSignal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            service.execute(runnable);
        }
        studentRunRace.waitStop();

        service.shutdown();
    }

}
