package com.jezz.springconsumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {
    /**
     * topics中填写在服务器上创建的topic即可
     * @param record
     */
    @KafkaListener(topics = {"test"})
    public void consumer(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()){
            System.out.println(kafkaMessage.get());
        }
    }
}
