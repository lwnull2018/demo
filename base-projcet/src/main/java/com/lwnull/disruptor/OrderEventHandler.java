package com.lwnull.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        String str = "event: %s, sequence: %s, endOfBatch: %s";
        str = String.format(str, event, sequence, endOfBatch);
        System.out.println(str);
    }

}
