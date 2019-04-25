package com.jezz.design.patterns.decorator;

/**
 * 具体组件实现类
 */
public class ConcreteComponent implements IComponent {

    /**
     * 构造器
     */
    public ConcreteComponent() {
        System.out.println("创建具体角色");
    }

    @Override
    public void operation() {
        System.out.println("调用具体角色的方法operation()");
    }
}
