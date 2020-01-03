//package com.jezz.springconsumer.listener;
//
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class KafkaListener {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @org.springframework.kafka.annotation.KafkaListener(topics = "thread_test")
//    public void listen0(List<ConsumerRecord<?, ?>> records, Acknowledgment ack, Consumer<?, ?> consumer){
//        for (ConsumerRecord<?, ?> record : records) {
//            logger.info("offset = " + record.offset() + "partation = " + record.partition()+ "key= " +  record.key()+ "value= " + record.value());
//        }
//        ack.acknowledge();
//    }
//}