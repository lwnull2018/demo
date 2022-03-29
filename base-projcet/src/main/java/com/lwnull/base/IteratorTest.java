package com.lwnull.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

/**
 * @Description iterator对象用于遍历Collection容器对象中的元素
 * @ClassName IteratorTest
 * @Author abc@123.com
 * @Date 2021/8/8 12:42 PM
 * @Version 1.0
 **/
public class IteratorTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equalsIgnoreCase("222")) {
                iterator.remove();
            }
            //赋值也不会改变容器中的值
            next = "xxx";
        }

        //遍历剩余的元素，上面的代码已经遍历完了，所以这里不会有任何输出
        iterator.forEachRemaining(e -> System.out.println(e));

        System.out.println("------");

        for (String s : list) {
            System.out.println(s);
        }

    }
}
