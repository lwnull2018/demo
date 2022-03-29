package com.lwnull.base;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @Description TreeSet自定义排序器
 * @ClassName TreeSetTest
 * @Author abc@123.com
 * @Date 2021/8/8 10:02 PM
 * @Version 1.0
 **/
public class TreeSetTest {

    public static void main(String[] args) {
        //定义一个TreeSet集合,并将自定义的排序器传给集合。这样该集合就会使用我们定义的排序规则来排序
        TreeSet treeset = new TreeSet(new myComp());
        treeset.add(new Person(10, "liuyia"));
        treeset.add(new Person(10, "liuyib"));
        treeset.add(new Person(15, "liuyi34"));
        treeset.add(new Person(11, "liuyi4"));
        treeset.add(new Person(12, "liuyi4"));

        Iterator itera = treeset.iterator();
        while (itera.hasNext()) {
            Person next = (Person)itera.next();
            System.out.println(next.getAge() + " " + next.getName());
        }
    }

}

class Person {
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class myComp implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Person p1 = (Person)o1;
        Person p2 = (Person)o2;

        return p1.getAge() - p2.getAge();
    }

}
