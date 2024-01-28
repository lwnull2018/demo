package com.lwnull.disruptor;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.copier.Copier;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class DisruptorDemo {

    public static void main(String[] args) {

        int bufferSize = 1024 * 1024;

        EventFactory<OrderEvent> aNew = OrderEvent::new;

        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );

        //定义一个消费者
        disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();
        //拿到 Disruptor 的环形缓冲区，并将其作为参数传递给生产者，供生产者放置数据
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        //定义一个生产
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);

        for (int i=0; i<10; i++) {
            //生产数据
            eventProducer.onData(UUID.randomUUID().toString());
        }

    }

}
