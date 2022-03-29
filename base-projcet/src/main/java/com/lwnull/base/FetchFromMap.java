package com.lwnull.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description 从Map获取数据的4种方式
 * @ClassName FetchFromMap
 * @Author abc@123.com
 * @Date 2021/8/7 3:54 PM
 * @Version 1.0
 **/
public class FetchFromMap {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);

        //1. entrySet遍历，在键和值都需要时使用（最常用）
        for (Map.Entry<Integer, Integer> entity : map.entrySet()) {
            System.out.println(String.format("key=%s, value=%s", entity.getKey(), entity.getValue()));
        }

        //2. 通过keySet或values来实现遍历,性能略低于第一种方式
        for (Integer key : map.keySet()) {
            System.out.println(key);
        }

        for (Integer value : map.values()) {
            System.out.println(value);
        }

        //3. 通过Iterator遍历
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            System.out.println(String.format("key=%s, value=%s", next.getKey(), next.getValue()));
        }

        //4. java8 Lambda
        //java8提供的Lambda表示式，语法更简洁，可以同时拿到key和value
        // 不过，经测试，性能低于entrySet,所以更推荐用entrySet的方式
        map.forEach((key, value) -> {
            System.out.println(String.format("key=%s, value=%s", key, value));
        });

    }
}
