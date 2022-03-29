package com.lwnull.base.volatiledemo;

/**
 * 如果对n的操作是原子级别的，最后输出的结果应该为n=1000，而在执行上面积代码时，很多时侯输出的n都小于1000，这说明n=n+1不是原子级别的操作。
 *  原因是声明为volatile的简单变量如果当前值由该变量以前的值相关，那么volatile关键字不起作用，也就是说如下的表达式都不是原子操作：
 *
 *      n  =  n  +   1 ;
 *      n ++ ;
 *
 *  如果要想使这种情况变成原子操作，需要使用synchronized关键字
 *
 *  保证原子操作，请查看 JoinThread2 类
 *
 * Created by abc@123.com ON 2018/11/25.
 */
public class JoinThread1 extends Thread {
    public static volatile int n = 0;

    public void run() {
        for (int i = 0; i < 10; i++)
            try {
                n = n + 1;
                sleep(3);  //  为了使运行结果更随机，延迟3毫秒

            } catch (Exception e) {
            }
    }

    public static void main(String[] args) throws Exception {

        Thread threads[] = new Thread[100];
        for (int i = 0; i < threads.length; i++)
            //  建立100个线程
            threads[i] = new JoinThread1();
        for (int i = 0; i < threads.length; i++)
            //  运行刚才建立的100个线程
            threads[i].start();
        for (int i = 0; i < threads.length; i++)
            //  100个线程都执行完后继续
            threads[i].join();
        System.out.println(" n= " + JoinThread1.n);
    }
}
