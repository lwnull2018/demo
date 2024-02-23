package com.jack.chen.thread;

public class LockSycnDemo {

    /*Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("---synchronized code block");
        }
    }
*/
    public static synchronized void m3() {
        System.out.println("---synchronized class code");
    }

    public static void main(String[] args) {

    }

}
