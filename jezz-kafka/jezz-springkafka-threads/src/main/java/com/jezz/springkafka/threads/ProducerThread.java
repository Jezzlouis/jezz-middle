package com.jezz.springkafka.threads;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ProducerThread implements Runnable {

    private static KafkaProducer<String, String> producer = null;

    /*
     * 初始化生产者
     */
    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
    }

    /*
     * 初始化配置
     */
    private static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return props;
    }

    @Override
    public void run() {
        System.out.println("主线程序号：" + Thread.currentThread().getId() + " ");
        int j = 0;
        while (true) {
            j++;
            // 消息实体
            ProducerRecord<String, String> record = null;
            for (int i = 0; i < 10; i++) {
                record = new ProducerRecord<String, String>("thread_test", "value" + i);
                // 发送消息
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (null != e) {
                            log.info("send error" + e.getMessage());
                        } else {
                            System.out.println("线程序号：" + Thread.currentThread().getId() + " " + String.format("发送信息---offset:%s,partition:%s", recordMetadata.offset(),
                                    recordMetadata.partition()));
                        }
                    }
                });
            }
            // producer.close();
            try {

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (j > 5) {
                break;
            }
        }

    }

    public static void main(String[] args) {
        ExecutorService runnableService = Executors.newFixedThreadPool(3);
        runnableService.submit(new ProducerThread());
        runnableService.submit(new ProducerThread());
        runnableService.submit(new ProducerThread());
        runnableService.shutdown();
    }
}