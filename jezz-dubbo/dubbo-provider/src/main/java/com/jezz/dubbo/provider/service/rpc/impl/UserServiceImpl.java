package com.jezz.dubbo.provider.service.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jezz.dubbo.service.api.user.UserService;
import org.springframework.stereotype.Component;

/**
 * @author jezzlouis
 */
@Service(interfaceClass = UserService.class)
@Component
public class UserServiceImpl implements UserService {
    @Override
    public void sayHello(String name) {
        System.out.println("hello ..." + name + "!!!");
    }
}
