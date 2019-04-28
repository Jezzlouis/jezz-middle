package com.jezz.design.patterns.factory.easy;

import com.jezz.design.patterns.factory.IFactory;
import com.jezz.design.patterns.factory.MailSender;
import com.jezz.design.patterns.factory.SmsSender;

public class EasyFactory {
    public IFactory getFactory(String type){
        if("mail".equalsIgnoreCase(type)){
            return new MailSender();
        }else if("sms".equalsIgnoreCase(type)){
            return new SmsSender();
        }
        System.out.println("...类型错误...");
        return null;
    }

}
