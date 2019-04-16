package com.jezz.dubbo.provider.service.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jezz.dubbo.service.api.car.CarService;
import org.springframework.stereotype.Component;

@Service(interfaceClass = CarService.class)
@Component
public class CarServiceImpl implements CarService {

    @Override
    public void makeCar(String name) {
        System.out.println("制造汽车..." + name);
    }
}
