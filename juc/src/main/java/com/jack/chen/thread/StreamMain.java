package com.jack.chen.thread;

import java.util.Arrays;
import java.util.List;

public class StreamMain {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("123", "456", "789");
        MyStream<String> myStream = new MyStream(list);
        myStream.myForEach(str -> System.out.println(str)); // 使用自定义函数接口书写Lambda表达式
    }

}

class MyStream<T> {
    private List<T> list;

    MyStream(List<T> ll) {
        this.list = ll;
    }

    public void myForEach(ConsumerInterface<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

}
