package com.jezz.shardingjdbc.controller;


import com.jezz.shardingjdbc.mapper.OrderMapper;
import com.jezz.shardingjdbc.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/add")
    public String add(){
        for(int i=0;i<10;i++){
            Order order = new Order();
            order.setOrderId((long) i);
            order.setUserId((long) i);
            orderMapper.save(order);
        }
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setOrderId(idGenerator.generateId().longValue());
//        repository.save(order);
        return "success";
    }

    @RequestMapping("/query")
    public List<Order> queryAll(){
        List<Order> orders = orderMapper.findAll();
        return orders;
    }
}