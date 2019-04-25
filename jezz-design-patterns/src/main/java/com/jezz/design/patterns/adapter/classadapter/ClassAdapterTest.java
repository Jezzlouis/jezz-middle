package com.jezz.design.patterns.adapter.classadapter;

import com.jezz.design.patterns.adapter.ITarget;
import com.jezz.design.patterns.adapter.classadapter.impl.ClassAdapter;

public class ClassAdapterTest {
    public static void main(String[] args) {
        System.out.println("类适配器模式测试：");
        ITarget target = new ClassAdapter();
        target.request();
    }
}
