package com.lwnull.base;

/**
 * 基础装箱、拆箱
 */
public class BaseInBoxing {

    public static void main(String[] args) {
        Integer i1 = 127;
        Integer i2 = 127;
        Integer i3 = 200;
        Integer i4 = 200;

        System.out.println(i1==i2);
        System.out.println(i3==i4);

        /**
         * 在通过valueOf方法创建Integer对象的时候，如果数值在[-128,127]之间，
         * 便返回指向IntegerCache.cache中已经存在的对象的引用；否则创建一个新的Integer对象
         *
         * public static Integer valueOf(int i) {
         *        if (i >= IntegerCache.low && i <= IntegerCache.high)
         *             return IntegerCache.cache[i + (-IntegerCache.low)];
         *        return new Integer(i);
         * }
         *
         */

        System.out.println("==============");

        Double d1 = 100.0;
        Double d2 = 100.0;
        Double d3 = 200.0;
        Double d4 = 200.0;

        System.out.println(d1==d2);
        System.out.println(d3==d4);

        /**
         * 在某个范围内整型数值个数是有限的，而浮点数却不是.
         * 浮点数都是创建新的Double对象.
         */

    }

}
