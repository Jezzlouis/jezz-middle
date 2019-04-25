package com.jezz.design.patterns.strategy;

import com.jezz.design.patterns.strategy.impl.ConcreteStrategy1;

public class StrategyClient {
    public static void main(String[] args) {
        // 1.创建具体策略实现
        IStrategy iStrategy = new ConcreteStrategy1();
        // 2.创建策略上下文,构造器注入策略实现对象
        StrategyContext strategyContext = new StrategyContext(iStrategy);
        // 3.调用策略上下文方法实现策略回调
        strategyContext.contextMethod();
    }
}
