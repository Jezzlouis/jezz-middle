package com.jezz.design.patterns.factory.abstractfac;

import com.jezz.design.patterns.factory.IFactory;
import com.jezz.design.patterns.factory.MailSender;
import com.jezz.design.patterns.factory.SmsSender;

public class MailFactory implements AbstractFactory {
    @Override
    public IFactory getInstance() {
        return new MailSender();
    }
}
