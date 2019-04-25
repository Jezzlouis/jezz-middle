package com.jezz.design.patterns.decorator;

/**
 * 具体去装饰装饰者
 */
public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(IComponent component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        addedFunction();
    }

    public void addedFunction()
    {
        System.out.println("为具体角色增加额外的功能addedFunction()");
    }
}
