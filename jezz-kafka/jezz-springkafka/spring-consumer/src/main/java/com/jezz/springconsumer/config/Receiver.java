package com.jezz.springconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

@Slf4j
public class Receiver {

    private static final String BATCH_TOPIC = "thread_test";

    @KafkaListener(topics = BATCH_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void receivePartitions(List<String> data,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                                  @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        for (int i = 0; i < data.size(); i++) {
            Long threadId = Thread.currentThread().getId();
            String orderStr = data.get(i);
            log.info("接收消息成功,任务号为:{},分区为{},处理线程为:{}", orderStr, partitions.get(i), threadId);
        }

    }

}