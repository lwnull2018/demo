package com.jack.chen.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CompletableFutureDemo {

    public static void main(String[] args) {

        FutureTask futureTask = new FutureTask<>(new MyThread());

        Thread thread = new Thread(futureTask, "t1");
        thread.start();

        try {
            System.out.println(futureTask.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {

        System.out.println("----come in call()");

        return "hell future task";
    }

}