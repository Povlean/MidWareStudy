package com.ean.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:TODO
 * @author:Povlean
 */
public class ProducerHelloWorld {
    public static void main(String[] args) throws IOException, TimeoutException {
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
        channel.queueDeclare("hello_world",true,false,false,null);

        String body = "hello rabbitmq~~";
        // 发送消息
        channel.basicPublish("","hello_world",null,body.getBytes());
        // 释放资源
        channel.close();
        connection.close();
    }
}
