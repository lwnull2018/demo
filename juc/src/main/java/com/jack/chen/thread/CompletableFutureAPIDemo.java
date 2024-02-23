package com.jack.chen.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

//        System.out.println(completableFuture.get());//不见不散，阻塞等待结果返回，同时有编译时异常需要处理
//        System.out.println(completableFuture.join());//跟get一样，只是没有编译时异常需要处理

//        System.out.println(completableFuture.getNow("123"));//马上就要得到值，如果未计算完成，则用参数中的 valueifAbsent

        TimeUnit.SECONDS.sleep(1);

        //complete是否打断get立即返回括号中的值
        System.out.println(completableFuture.complete("abcd") + "\t" + completableFuture.join());

    }

}
