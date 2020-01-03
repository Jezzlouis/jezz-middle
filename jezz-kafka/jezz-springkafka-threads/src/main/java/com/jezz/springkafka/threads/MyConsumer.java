package com.jezz.springkafka.threads;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MyConsumer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private KafkaConsumer<String, String> consumer;
    public static void main(String[] args) {
        Properties props = MyConsumer.getProperties();
        MyConsumer myConsumer = new MyConsumer();
        myConsumer.multiThread(props);
       // myConsumer.singleThread(props);
    }
    @Async
    public void multiThread(Properties props) {

        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList("thread_test"));
        execute();
    }
    private void execute() {
        try {
            ExecutorService executors = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(200);
                for (final ConsumerRecord record : records) {
                    //获取新这个partition中的最后一条记录的offset并加1 那么这个位置就是下一次要提交的offset
                    ConsumerRunner consumerRunner = new ConsumerRunner(consumer, record);
                    executors.submit(consumerRunner);
                }
                consumer.commitAsync();
            }
        }
        finally {
            consumer.close();
        }
    }

    private void singleThread(Properties props) {
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList("thread_test"));
        ConsumerRunner consumerRunner = new ConsumerRunner(consumer);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                logger.info("offset = " + record.offset() + "partation = " + record.partition()+ "key= " +  record.key()+ "value= " + record.value());
            }
        }
    }

    public static Properties getProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
class ConsumerRunner implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    KafkaConsumer consumer;
    ConsumerRecord record;
    ConsumerRunner(KafkaConsumer consumer) {
        this.consumer = consumer;
    }
    ConsumerRunner(KafkaConsumer consumer, ConsumerRecord record) {
        this.consumer = consumer;
        this.record = record;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getId()+ "=======" + "offset = " + record.offset() + "partation = " + record.partition()+ "key= " +  record.key()+ "value= " + record.value());
    }
}