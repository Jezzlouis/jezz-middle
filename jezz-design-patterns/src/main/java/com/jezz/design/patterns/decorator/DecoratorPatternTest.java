package com.jezz.design.patterns.decorator;

public class DecoratorPatternTest {
    public static void main(String[] args) {
        IComponent p = new ConcreteComponent();
        p.operation();
        System.out.println("---------------------------------");
        IComponent d = new ConcreteDecorator(p);
        d.operation();
    }

}
