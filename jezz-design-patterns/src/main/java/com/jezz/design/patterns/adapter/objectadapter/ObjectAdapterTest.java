package com.jezz.design.patterns.adapter.objectadapter;

import com.jezz.design.patterns.adapter.Adaptee;
import com.jezz.design.patterns.adapter.ITarget;

public class ObjectAdapterTest {
    public static void main(String[] args) {
        System.out.println("对象适配器模式测试：");
        Adaptee adaptee = new Adaptee();
        ITarget target = new ObjectAdapter(adaptee);
        target.request();
    }
}
