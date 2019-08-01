package com.jezz.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jezz.dubbo.consumer.service.LoginService;
import com.jezz.dubbo.service.api.car.CarService;
import com.jezz.dubbo.service.api.user.UserService;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Reference(interfaceClass = UserService.class,check = false,retries = 2)
    private UserService userService;
    @Reference(interfaceClass = CarService.class,check = false,retries = 2)
    private CarService carService;

    @Override
    public void sayHello(String name) {
        userService.sayHello(name);
    }

    @Override
    public void makeCar(String name) {
        carService.makeCar(name);
    }
}
