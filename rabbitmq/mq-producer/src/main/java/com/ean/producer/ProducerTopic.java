package com.ean.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @description:TODO
 * @author:Povlean
 */
public class ProducerTopic {
    public static void main(String[] args) throws Exception{
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
        // channel.queueDeclare("hello_world",true,false,false,null);
        String exchangeName = "test_topic";
        // 创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);
        // 创建队列
        String queueName1 = "test_topic_queue1";
        String queueName2 = "test_topic_queue2";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);
        // 绑定队列和交换机
        // 队列1
        channel.queueBind(queueName1,exchangeName,"*.error");
        // 队列2
        channel.queueBind(queueName2,exchangeName,"info.*");
        channel.queueBind(queueName2,exchangeName,"info.error");
        channel.queueBind(queueName2,exchangeName,"*.*");
        String body = "log info ==> invoke findAll()";
        // 发送消息
        channel.basicPublish(exchangeName,"info.warning",null,body.getBytes());
        // 释放资源
        channel.close();
        connection.close();
    }
}
