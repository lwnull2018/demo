package com.jack.chen.thread.objecthead;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JolDemo {

    public static void main(String[] args) {

/*

        System.out.println(VM.current().details());

        System.out.println("======");

        System.out.println(VM.current().objectAlignment());

        System.out.println("======");

        System.out.println(VM.current().objectHeaderSize());
*/



        Object o = new Object();//16 bytes

        System.out.println("10进制:" + o.hashCode());
        System.out.println("16进制:" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制:" + Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

    }
}


class Customer {
    int age;
    boolean flag = false;
}
