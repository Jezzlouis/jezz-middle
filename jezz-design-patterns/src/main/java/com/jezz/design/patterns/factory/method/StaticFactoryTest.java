package com.jezz.design.patterns.factory.method;

import com.jezz.design.patterns.factory.IFactory;

public class StaticFactoryTest {
    public static void main(String[] args) {
        IFactory factory = StaticFactory.getMailFactory();
        factory.sendMsg();
    }
}
