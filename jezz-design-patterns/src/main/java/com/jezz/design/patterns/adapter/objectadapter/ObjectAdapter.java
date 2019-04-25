package com.jezz.design.patterns.adapter.objectadapter;

import com.jezz.design.patterns.adapter.Adaptee;
import com.jezz.design.patterns.adapter.ITarget;

public class ObjectAdapter implements ITarget {

    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
