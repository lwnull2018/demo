package com.jack.chen.thread.objecthead;

/**
 * 锁消除示例
 * 从JIT角度看相当于无视它，synchronized(o)不存在了
 * 这个锁对象并没有被共用扩散到其他线程使用
 * 极端的说法就是根本没有加锁对象的底层机器码，消除了锁的使用
 */
public class LockClearUpDemo {

    static Object object = new Object();

    private void m1() {
        //锁消除问题，JIT会无视它，synchronized(o)每次new出来的，都不存在了，非正常的
        Object o = new Object();
        synchronized (o) {
            System.out.println("------LockClearUpDemo \t" + o.hashCode() + "\t" + object.hashCode());
        }
    }

    public static void main(String[] args) {
        LockClearUpDemo lockClearUpDemo = new LockClearUpDemo();
        for (int i = 1; i <= 10; i++) {
            lockClearUpDemo.m1();
            new Thread(()->{

            }, String.valueOf(i)).start();
        }
    }
}
/**
 * ------LockClearUpDemo 	1159190947	925858445
 * ------LockClearUpDemo 	681842940	925858445
 * ------LockClearUpDemo 	1392838282	925858445
 * ------LockClearUpDemo 	523429237	925858445
 * ------LockClearUpDemo 	664740647	925858445
 * ------LockClearUpDemo 	804564176	925858445
 * ------LockClearUpDemo 	1421795058	925858445
 * ------LockClearUpDemo 	1555009629	925858445
 * ------LockClearUpDemo 	41359092	925858445
 * ------LockClearUpDemo 	149928006	925858445
 */
