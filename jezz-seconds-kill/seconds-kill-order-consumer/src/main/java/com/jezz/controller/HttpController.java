package com.jezz.controller;


import com.jezz.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class HttpController {

    Logger logger = LoggerFactory.getLogger(HttpController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "test")
    public String test(){
        orderService.createWrongOrder(1);
        return "hello";
    }

    @RequestMapping(value = "optest")
    public void optest(){
        try {
            orderService.createOptimisticOrder(1);
        } catch (Exception e) {
            logger.error("库存不足",e);
        }
    }

}
