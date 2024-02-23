package com.jack.chen.thread.atomic;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    String username;
    int age;
}

public class AtomicReferenceDemo {

    public static void main(String[] args) {

        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 32);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get());//true	User(username=li4, age=32)
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get());//false	User(username=li4, age=32)

    }

}
