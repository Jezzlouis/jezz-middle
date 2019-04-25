package com.jezz.design.patterns.decorator;

/**
 * 装饰者 实现具体组件接口
 */
public class Decorator implements IComponent {

    private IComponent component;

    /**
     * 构造器注入
     * @param component
     */
    public Decorator(IComponent component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
