package com.lwnull.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 请描述类的业务用途
 * @ClassName FetchFromList
 * @Author lwnull2018@gmail.com
 * @Date 2021/8/7 4:14 PM
 * @Version 1.0
 **/
public class FetchFromList {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);

        //1.普通遍历方式
        for (int i=0, len=list.size(); i<len; i++) {
            System.out.println(list.get(i));
        }

        //2.增强for循环
        for (Integer i : list) {
            System.out.println(i);
        }

        //3. 使用Iterator迭代器
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }


        //4. Java8 Lambda表达式
        list.forEach((val -> {
            System.out.println(val);
        }));

    }

}
