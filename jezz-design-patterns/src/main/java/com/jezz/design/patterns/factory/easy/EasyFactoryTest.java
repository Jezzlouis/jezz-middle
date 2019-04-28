package com.jezz.design.patterns.factory.easy;

import com.jezz.design.patterns.factory.IFactory;

public class EasyFactoryTest {
    public static void main(String[] args) {
        EasyFactory easyFactory = new EasyFactory();
        IFactory factory = easyFactory.getFactory("sms");
        factory.sendMsg();
    }
}
