package com.jack.chen.thread.lock;


import java.util.concurrent.TimeUnit;

class MyCount {
    volatile int count = 0;

    /**
     * add方法加 synchronized 才能保证运行结果 10*1000 = 10000
     */
     void add() {
        count++;
    }

    int getCount() {
        return count;
    }
}

public class VolatileNoAtomicDemo {


    public static void main(String[] args) {

        MyCount myCount = new MyCount();

        for (int i=0; i<10; i++) {
            new Thread(()->{
                for (int j=0; j<1000; j++) {
                    myCount.add();
                }
            }, String.valueOf(i+1)).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t count=" + myCount.getCount());

    }

}
