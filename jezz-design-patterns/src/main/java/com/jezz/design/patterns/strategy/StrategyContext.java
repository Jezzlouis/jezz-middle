package com.jezz.design.patterns.strategy;
// 策略上下文
public class StrategyContext {
    // 策略引用
    private IStrategy iStrategy;
    // 使用构造器注入

    public StrategyContext(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    public void contextMethod(){
        // 调用引用策略的方法
        iStrategy.requestMethod();
    }
}
