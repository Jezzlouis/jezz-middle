package com.jezz.design.patterns.factory.abstractfac;

import com.jezz.design.patterns.factory.IFactory;
import com.jezz.design.patterns.factory.SmsSender;

public class SmsFactory implements AbstractFactory {
    @Override
    public IFactory getInstance() {
        return new SmsSender();
    }
}
