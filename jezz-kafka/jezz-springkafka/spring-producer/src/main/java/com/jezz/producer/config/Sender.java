package com.jezz.producer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public void send(String topic, String data) {
        kafkaTemplate.send(topic, data);
    }

    public void send(String topic, int partition, String data) {
        kafkaTemplate.send(topic, partition, data);
    }
}