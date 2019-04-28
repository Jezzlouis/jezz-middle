package com.jezz.design.patterns.factory;

import com.jezz.design.patterns.factory.IFactory;

public class MailSender implements IFactory {
    @Override
    public void sendMsg() {
        System.out.println("this is mail sender");
    }
}
