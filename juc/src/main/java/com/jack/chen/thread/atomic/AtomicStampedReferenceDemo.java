package com.jack.chen.thread.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
class Book {
    int id;
    String bookName;
}

public class AtomicStampedReferenceDemo {

    public static void main(String[] args) {
        Book javaBook = new Book(100, "JavaBook");

        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook, 1);

        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        Book mysqlBook = new Book(101, "MySqlBook");

        boolean b = atomicStampedReference.compareAndSet(javaBook, mysqlBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        boolean b1 = atomicStampedReference.compareAndSet(mysqlBook, javaBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

        System.out.println(b1 + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

    }
}
/**
 * Book(id=100, bookName=JavaBook)	1
 * true	Book(id=101, bookName=MySqlBook)	2
 * true	Book(id=100, bookName=JavaBook)	3
 */
