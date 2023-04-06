package com.ean.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:TODO
 * @author:Povlean
 */
public class ConsumerRouting01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 工厂代码
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置参数
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建链接Connection
        Connection connection = factory.newConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 创建队列Queue
        String queueName1 = "test_direct_queue1";
        String queueName2 = "test_direct_queue2";

        // 消费者代码
        Consumer consumer = new DefaultConsumer(channel) {
            /*
                回调函数，当收到消息后，会自动执行该方法

                1. ConsumerTag 标识
                2. envelope 获取信息，交换机，路由key
                3. properties 配置信息
                4. body 数据
            */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // System.out.println("consumerTag:" + consumerTag);
                // System.out.println("Exchange:" + envelope.getExchange());
                // System.out.println("RoutingKey:" + envelope.getRoutingKey());
                // System.out.println("properties:" + properties);
                System.out.println("body:" + new String(body));
                System.out.println("将error信息保存到数据库");
            }
        };
        // 消费消息
        channel.basicConsume(queueName1,true,consumer);
    }
}
