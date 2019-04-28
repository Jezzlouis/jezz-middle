package com.jezz.design.patterns.factory.abstractfac;


import com.jezz.design.patterns.factory.IFactory;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        AbstractFactory abstractFactory = new SmsFactory();
        IFactory factory = abstractFactory.getInstance();
        factory.sendMsg();
    }
}
