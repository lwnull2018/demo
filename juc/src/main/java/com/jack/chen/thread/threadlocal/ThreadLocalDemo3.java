package com.jack.chen.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SellHouse {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()-> 0);

    public void sellByThreadLocal() {
        threadLocal.set(1 + threadLocal.get());
    }
}

public class ThreadLocalDemo3 {

    public static void main(String[] args) {

        SellHouse sellHouse = new SellHouse();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try {
            for (int i = 1; i <=10 ; i++) {
                executorService.submit(()->{
                    try {
                        int defaultVal = sellHouse.threadLocal.get();
                        sellHouse.sellByThreadLocal();
                        System.out.println(Thread.currentThread().getName() + "\t" + defaultVal + "\t" + sellHouse.threadLocal.get());
                    } finally {
                        //删除此线程局部变量当前值，否则在线程池的环境下，可能会出现重复使用上一个使用者遗留值的情况
                        sellHouse.threadLocal.remove();
                    }

                });
            }
        } finally {
            executorService.shutdown();
        }

    }

}
/**
 * pool-1-thread-3	0	1
 * pool-1-thread-1	0	1
 * pool-1-thread-2	0	1
 * pool-1-thread-2	0	1
 * pool-1-thread-1	0	1
 * pool-1-thread-2	0	1
 * pool-1-thread-1	0	1
 * pool-1-thread-3	0	1
 * pool-1-thread-1	0	1
 * pool-1-thread-2	0	1
 */
