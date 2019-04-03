package com.jezz.gc;

import org.junit.Test;

public class GCTest {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    /**
     * -verbose:gc
     */
    @Test
    public void test1(){
        GCTest objectA = new GCTest();
        GCTest objectB = new GCTest();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;
        System.gc();
    }
}
