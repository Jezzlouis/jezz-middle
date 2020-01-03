package com.jezz.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.DelayQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaApplicationTest {

    private static String BATCH_TOPIC = "batch.t";

    private static Integer PARTITIONS = 6;
    /**
     * 已支付
     */
    private static Integer PAYED_STATUS = 2;
    /**
     * 已取走
     */
    private static Integer SEND_BACK_STATUS = 3;

    @Autowired
    private Sender sender;

    private static DelayQueue delayQueue = new DelayQueue();

    @Test
    public void testReceive() throws Exception {
//        for (int i = 1; i < 50; i++) {
//            Integer orderNum = 800010 + i;
//            Random random = new Random();
//            Integer orderPrice = random.(1, 20);
//            // 用户支付成功，订单状态为支付成功
//            OrderDTO order = new OrderDTO(orderNum, orderPrice, PAYED_STATUS);
//            // 发送支付成功订单消息到对应的kafka分区
//            Integer destinationPartition = orderNum % PARTITIONS;
//            sender.send(BATCH_TOPIC, destinationPartition, JSONUtil.toJsonStr(order));
//            // 创建任务放入延迟队列（模拟用户支付成功到取走充电宝花费的时间）
//            long delayTime = 200;
//            OrderTask orderTask = new OrderTask(delayTime, order);
//            delayQueue.offer(orderTask);
//        }
//
//        while (true) {
//            // 用户取走充电宝,订单状态更改为 已取走
//            OrderTask orderTask = (OrderTask) delayQueue.take();
//            OrderDTO orderDTO = orderTask.getOrderDTO();
//            Integer destinationPartition = orderDTO.getOrderNum() % PARTITIONS;
//            orderDTO.setOrderStatus(SEND_BACK_STATUS);
//            // 发送已取走订单消息到对应的kafka 分区
//            sender.send(BATCH_TOPIC, destinationPartition, JSONUtil.toJsonStr(orderDTO));
//        }

    }
}