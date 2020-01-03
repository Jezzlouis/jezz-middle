package com.jezz.springkafka.threads;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class MyProducer {

    public static void main(String[] args) {
        Properties properties =  MyProducer.getProperties();
        Producer<String, String> producer = new KafkaProducer<>(properties);
        Random random = new Random();
        int i = 0;
        while (true) {
            producer.send(new ProducerRecord<String, String>("test", String.valueOf(random.nextInt(100)),String.valueOf(i++)));
        }
    }
    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all"); //判断是否发送成功,不成功会阻塞所有消息,性能低,但是可靠性高
        props.put("retries", 0); // 请求失败不自动重试,启用重试,可能会出现消息重复
        props.put("batch.size", 16384); //缓存区域大小
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432); // 生产者可用的缓存总量
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}