package com.jezz.design.patterns.factory.method;

import com.jezz.design.patterns.factory.IFactory;
import com.jezz.design.patterns.factory.MailSender;
import com.jezz.design.patterns.factory.SmsSender;

public class StaticFactory {
    public static IFactory getSmsFactory(){
        return new SmsSender();
    }
    public static IFactory getMailFactory(){
        return new MailSender();
    }
}
