package com.jack.chen.newfeature.flowapi;

import java.util.Scanner;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @Description 请描述类的业务用途
 * @ClassName FlowFilterDemo
 * @Author abc@123.com
 * @Date 2021/10/9 4:06 PM
 * @Version 1.0
 **/
public class FlowFilterDemo {


    public static void main(String[] args) {


        //内部类 过滤器类
        class DataFilter extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                this.submit("【这是一条被处理过的数据】" + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                this.close();
            }

        }

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        DataFilter dataFilter = new DataFilter();
        publisher.subscribe(dataFilter);//此时dataFilter作为消息消费者

        //消费者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("接收到 publisher 发来的消息了：" + item);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                //出现异常，就会来到这个方法，此时直接取消订阅即可
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                this.subscription.cancel();;
            }
        };

        dataFilter.subscribe(subscriber);//此时dataFilter作为消息发送者

        for (int i = 0; i < 500; i++) {
            System.out.println("发送消息 i------>" + i);
            publisher.submit("hello:" + i);
        }

        publisher.close();
        new Scanner((System.in)).next();

    }


}
