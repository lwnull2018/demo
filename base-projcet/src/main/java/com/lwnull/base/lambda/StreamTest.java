package com.lwnull.base.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description 请描述类的业务用途
 * @ClassName StreamTest
 * @Author abc@123.com
 * @Date 2021/10/7 3:53 PM
 * @Version 1.0
 **/
public class StreamTest {

    public static void main(String[] args) {

//        flatMap();

//        peek();

//        distinct();

//        limitSkip();

//        forEach();


//        reduce();

//        min();

//        findFirst();

//        allMatch();

        parallel();

    }

    /**
     * 并行处理，加快处理速度
     */
    private static void parallel() {
        new Random().ints().limit(50).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName() + "--->" + i);
        });
    }

    /**
     * 所有元素是否满足给定的条件
     */
    private static void allMatch() {
        boolean b = Stream.of(1,2,3,4).allMatch(i -> i < 5);
        System.out.println("b = " + b);
    }

    private static void findFirst() {
        for (int i = 0; i < 10; i++) {
            Optional<Integer> first = Stream.of(0, 1, 2, 3, 4).parallel().findFirst();
            System.out.println("first.get() = " + first.get());
        }

        System.out.println("=============");
        for (int i = 0; i < 10; i++) {
            Optional<Integer> first = Stream.of(5,6,2,7,8,9).parallel().findAny();
            System.out.println("first.get() = " + first.get());
        }

    }

    private static void min() {
        Stream<Integer> s = Stream.of(1, 2, 3, 4);
        Optional<Integer> min = s.min(Comparator.comparingInt(i -> i));
        System.out.println("min.get() = " + min.get());
    }

    /**
     * 聚合方法，将Stream所有元素按照聚合函数聚合成一个结果
     */
    private static void reduce() {
        Optional<Integer> optional = Stream.of(1, 2, 3, 4, 5).reduce((i, j) -> i + j);
        System.out.println(optional.orElse(-1));
    }


    private static void forEach() {

        int[] arr = {1,0, 2,3,4,5,6,7,8,9};

        Arrays.stream(arr).parallel().forEach(System.out::println);
        System.out.println("============");
        // forEachOrdered 保证并行流执行顺序就是按照数组中数据的顺序
        Arrays.stream(arr).parallel().forEachOrdered(System.out::println);

    }


    private static void limitSkip() {

        Arrays.asList("A", "B", "C", "D", "E", "F", "G").stream().skip(2).limit(3).forEach(System.out::println);

    }

    private static void distinct() {

        IntStream.of(2,3,4,3,5,4,6,7).distinct().forEach(System.out::println);

    }

    private static void flatMap() {

        Stream<Integer> s = Stream.of(new Integer[]{1, 2, 3}, new Integer[]{4, 5, 6}, new Integer[]{7, 8, 9}).flatMap( i -> Arrays.stream(i));
        s.forEach(System.out::println);

    }

    private static void peek() {
        IntStream.of(2,3,4,5,6,7).filter(i -> i>3).peek(String::valueOf).forEach(i -> System.out.println(i));
    }

}
