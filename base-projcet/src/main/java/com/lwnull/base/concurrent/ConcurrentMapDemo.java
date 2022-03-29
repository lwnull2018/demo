package com.lwnull.base.concurrent;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description ConcurrentHashMap使用
 * @ClassName ConcurrentMapDemo
 * @Version 1.0
 **/
public class ConcurrentMapDemo {

    private static ConcurrentMap<Integer, String> concurrentMap = new ConcurrentHashMap<Integer, String>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        ConcurrentMapDemo demo = new ConcurrentMapDemo();
        service.execute(demo.new WriteTheadOne());
        service.execute(demo.new WriteTheadTwo());
        service.execute(demo.new ReadThead());
        service.shutdown();
    }

    class WriteTheadOne implements Runnable {
        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                String s = concurrentMap.putIfAbsent(i, "A-" + i);
                System.out.println("A s="+s);
            }
        }
    }

    class WriteTheadTwo implements Runnable {
        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                String s = concurrentMap.putIfAbsent(i, "B-" + i);
                System.out.println("B s="+s);
            }
        }
    }

    class ReadThead implements Runnable {
        @Override
        public void run() {
            Iterator<Integer> iterator = concurrentMap.keySet().iterator();
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                String value = concurrentMap.get(key);
                System.out.println(key+":"+value);
            }
        }
    }

}
