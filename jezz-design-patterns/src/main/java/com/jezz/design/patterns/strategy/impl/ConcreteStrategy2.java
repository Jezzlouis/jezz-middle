package com.jezz.design.patterns.strategy.impl;

import com.jezz.design.patterns.strategy.IStrategy;

//实现的策略2
public class ConcreteStrategy2 implements IStrategy {

    @Override
    public void requestMethod() {
        System.out.println("...采用策略2222...");
    }
}
