package com.jack.chen.thread.objecthead;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedUpDemo {

    public static void main(String[] args) {
        /**
         * 这里偏向锁在JDK6以上默认开启，开启后程序启动几秒后才会被激活，可以通过JVM参数来关闭延迟 -XX:BiasedLockingStartupDelay=0
         */
//        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

}
