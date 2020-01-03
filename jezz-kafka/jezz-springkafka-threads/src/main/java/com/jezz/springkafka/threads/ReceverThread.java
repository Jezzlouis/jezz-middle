package com.jezz.springkafka.threads;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ReceverThread implements Runnable {

    private static KafkaConsumer<String, String> consumer;

    /**
     * 初始化消费者
     */
    static {
        Properties configs = initConfig();
        consumer = new KafkaConsumer<String, String>(configs);
        consumer.subscribe(Arrays.asList("thread_test"));
    }

    /**
     * 初始化配置
     */
    private static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void run() {
        System.out.println("主线程序号：" + Thread.currentThread().getId() + " ");

//		int i = 1 ;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            records.forEach((ConsumerRecord<String, String> record) -> {

                log.info("线程序号：" + Thread.currentThread().getId() + " partition:" + record.partition() + " 收到消息: key ===" + record.key() + " value ====" + record.value() + " topic ==="
                        + record.topic());
            });
//			 i++;
//	            //每次拉10条CONSUMER_MAX_POLL_RECORDS = 10;
//	            if (i >5 ){
//	                consumer.commitSync();
//
//	                break;
//	            }
        }
//		  consumer.close();
    }

    public static void main(String[] args) {
        ExecutorService runnableService = Executors.newFixedThreadPool(3);
        runnableService.submit(new ReceverThread());
        runnableService.submit(new ReceverThread());
        runnableService.submit(new ReceverThread());
        runnableService.shutdown();
    }
}