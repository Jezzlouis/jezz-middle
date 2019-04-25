package com.jezz.design.patterns.adapter.classadapter.impl;

import com.jezz.design.patterns.adapter.Adaptee;
import com.jezz.design.patterns.adapter.ITarget;

/**
 * 类适配器类
 */
public class ClassAdapter extends Adaptee implements ITarget {

    @Override
    public void request() {
        specificRequest();
    }

}
