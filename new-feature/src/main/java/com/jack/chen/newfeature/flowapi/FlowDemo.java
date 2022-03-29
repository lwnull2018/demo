package com.jack.chen.newfeature.flowapi;


import java.util.Scanner;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;


/**
 * JDK 9 推出 Flow APi
 * @Description 消息订阅初体验
 * @ClassName FlowDemo
 * @Author abc@123.com
 * @Date 2021/10/8 3:55 PM
 * @Version 1.0
 **/
public class FlowDemo {


    public static void main(String[] args) {


        //消息发布者
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        //订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅成功");
                this.subscription = subscription;
                //向数据发布者请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("接收到 publisher 发来的消息子: " + item);

                //接收完成后，可以继续接收或者不接收了
                //this. subscription.cancel();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("出现异常，取消订阅");
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                //发布者的所有数据被接收，并且发布者已经关闭
                System.out.println("数据接收完毕");
            }
        };

        //配置发布者和订阅者
        publisher.subscribe(subscriber);

        for (int i=0; i < 500; i++) {
            System.out.println("i------>" + i);
            //发送数据
            publisher.submit("hello:" + i);
        }

        //关闭发布者
        publisher.close();
        //让程序不要停止，观察消息订阅者打印情况
        new Scanner(System.in).next();

    }


}
