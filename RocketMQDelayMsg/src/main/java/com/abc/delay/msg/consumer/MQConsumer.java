package com.abc.delay.msg.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * 消息消费者
 */
public class MQConsumer {

    public static void main(String[] args) throws MQClientException {

        // 创建DefaultMQPushConsumer类并设定消费者名称
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer("consumer-group-test");

        // 设置NameServer地址，如果是集群的话，使用分号;分隔开
        mqPushConsumer.setNamesrvAddr("127.0.0.1:9876");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果不是第一次启动，那么按照上次消费的位置继续消费
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置消费模型，集群还是广播，默认为集群
        mqPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        // 消费者最小线程量
        mqPushConsumer.setConsumeThreadMin(5);

        // 消费者最大线程量
        mqPushConsumer.setConsumeThreadMax(10);

        // 设置一次消费消息的条数，默认是1
        mqPushConsumer.setConsumeMessageBatchMaxSize(1);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息，如果订阅该主题下的所有tag，则使用*
        mqPushConsumer.subscribe("MsgDelayTopic", "*");

        // 注册回调实现类来处理从broker拉取回来的消息
        mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            // 监听类实现MessageListenerConcurrently接口即可，重写consumeMessage方法接收数据
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt message : msgList) {
                    String body = new String(message.getBody(), StandardCharsets.UTF_8);
                    System.out.println("消费者接收到消息: " + message.toString() + "---消息内容为：" + body + "消息被消费时间：" + new Date(System.currentTimeMillis()) + ", 消息存储时间: " + new Date(message.getBornTimestamp()));
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者实例
        mqPushConsumer.start();
    }
}
