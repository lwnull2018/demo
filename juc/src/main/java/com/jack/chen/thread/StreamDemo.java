package com.jack.chen.thread;

import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        String str = "my name is 007";

        /*Stream.of(str.split(" "))
                .filter(s -> s.length() > 2)
                .map(s -> s.length())
                .forEach(System.out::println);*/

        Stream.of(str.split(" "))
                .flatMap(s -> s.chars().boxed())
                .forEach(i -> System.out.println((char)i.intValue()));
    }

}
