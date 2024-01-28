package com.abc.delay.msg.send;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Producer端发送延迟消息：只需在发送消息之前调用setDelayTimeLevel()方法设置消息的延迟等级即可
 */
public class SyncMQProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        // 创建DefaultMQProducer类并设定生产者名称
        DefaultMQProducer mqProducer = new DefaultMQProducer("producer-group-test");
        // 设置NameServer地址，如果是集群的话，使用分号;分隔开
        mqProducer.setNamesrvAddr("127.0.0.1:9876");
        // 消息最大长度 默认4M
        mqProducer.setMaxMessageSize(4096);
        // 发送消息超时时间，默认3000
        mqProducer.setSendMsgTimeout(3000);
        // 发送消息失败重试次数，默认2
        mqProducer.setRetryTimesWhenSendAsyncFailed(2);
        // 启动消息生产者
        mqProducer.start();

        // 创建消息，并指定Topic(主题)，Tag(标签)和消息内容
        Message message = new Message("MsgDelayTopic", "", "Hi, 这是测试延迟消息".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // 设置延时等级为3, 所以这个消息将在10s之后发送, RocketMQ目前只支持固定的几个延时时间，还不支持自定义延迟时间
        //有如下18个等级：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(4);

        // 发送同步消息到一个Broker，可以通过sendResult返回消息是否成功送达
        SendResult sendResult = mqProducer.send(message);
        System.out.println(sendResult);

        // 如果不再发送消息，关闭Producer实例
        mqProducer.shutdown();
    }

}
