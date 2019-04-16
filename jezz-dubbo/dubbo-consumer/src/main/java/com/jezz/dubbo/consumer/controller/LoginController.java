package com.jezz.dubbo.consumer.controller;

import com.jezz.dubbo.consumer.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dubbo")
public class LoginController {

    @Autowired
    private LoginService userService;

    @RequestMapping(value = "sayhello",method = RequestMethod.GET)
    public void sayHello(){
        userService.sayHello("JEZZ");
    }

}
