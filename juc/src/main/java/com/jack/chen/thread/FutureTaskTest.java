package com.jack.chen.thread;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * get() 是一种阻塞的方式，不管任务是否完成都会阻塞
 *
 * isDone() 检查任务是否执行完成，给出响应。如果想要异步获取结果，通常会以轮询的方式去获取，尽量不要阻塞
 */
public class FutureTaskTest {

    public static void main(String[] args) throws Exception {

        FutureTask task = new FutureTask(() -> {
            System.out.println(Thread.currentThread().getName() +" \t coming...");
            TimeUnit.SECONDS.sleep(3);
            return "task over";
        });

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("主线程去忙别的了...");

        //轮询的方式会消耗CPU资源，而且不见得能及时得到反馈结果
        while (true) {
            if (task.isDone()) {
                break;
            }
            System.out.println("task 任务正在执行，请稍等...");
            TimeUnit.MILLISECONDS.sleep(500);
        }

        System.out.println(task.get());//不见不散，只要出现get方法就会阻塞

    }

}
